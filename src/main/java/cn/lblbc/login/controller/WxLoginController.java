package cn.lblbc.login.controller;

import cn.lblbc.login.bean.Role;
import cn.lblbc.login.bean.UserDetail;
import cn.lblbc.login.bean.response.LoginResp;
import cn.lblbc.login.bean.response.Resp;
import cn.lblbc.login.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 蓝不蓝编程
 * @Description: [微信小程序登录获取用户信息接口, 解密数据获取用户信息包括UnionID(需要微信开放平台认证开发者账户绑定的小程序)]
 */

@RestController
@RequestMapping("/user")
public class WxLoginController {

    public static final String APPID = "wx7b05378f34424e99"; //申请小程序的AppId
    public static final String APP_SECRET = "60c06aca0abb3938f00849f6614d4789"; //生成的AppSecret

    //请求微信后端的地址
    public static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

    @Autowired
    private AuthService authService;

    public static final String DEFAULT_PASSWORD = "123456";


    /**
     * 登录接口
     *
     * @param code 为前端wx.login获取的code
     */
    @PostMapping("/loginByWx")
    public Resp<LoginResp> wxLogin(@RequestParam("code") String code) throws Exception {

        //装载请求参数的Map集合,通过code,appid,app_secret获取用户的OpenId和session_key
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appid", APPID);
        paramsMap.put("secret", APP_SECRET);
        paramsMap.put("js_code", code);
        paramsMap.put("grant_type", "authorization_code");

        Map<String, String> resultMap = getWxOpenIdAndSkey(AUTH_URL, paramsMap);
        System.out.println(resultMap);

        String openId = resultMap.get("openid");
        Resp<LoginResp> resp;

        if (openId == null || openId.isEmpty()) {
            resp = new Resp<>(Resp.ERROR, "登录失败");
        } else {
            try {
                resp = authService.login(openId, DEFAULT_PASSWORD);
            } catch (Exception e) {
                long defaultRoleId = 1L;
                UserDetail userDetail = new UserDetail(openId, DEFAULT_PASSWORD, new Role(defaultRoleId));
                authService.register(userDetail);
                resp = authService.login(openId, DEFAULT_PASSWORD);
            }
        }

        return resp;
    }

    /**
     * 请求用户 openid 和 session_key的方法
     *
     * @param url       请求的地址
     * @param paramsMap 参数map
     * @return
     * @throws Exception
     */
    private Map<String, String> getWxOpenIdAndSkey(String url, Map<String, String> paramsMap) throws Exception {

        //使用spring的restTemplate来发送http请求
        RestTemplate restTemplate = new RestTemplate();

        //拿到http请求的response的所有内容
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, paramsMap);

        //拿到body的内容
        String jsonResult = responseEntity.getBody();

        //使用jackson来完成类型转换
        ObjectMapper objectMapper = new ObjectMapper();
        //获取map类型是Map<String, String>
        MapType mapType = TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class);
        //将json格式的字符串转换为Map
        Map<String, String> resultMap = null;
        try {
            resultMap = objectMapper.readValue(jsonResult, mapType);
        } catch (IOException e) {
            throw new IOException("内容json转Map转换异常");
        }

        return resultMap;
    }
}
