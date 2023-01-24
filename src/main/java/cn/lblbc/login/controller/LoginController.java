package cn.lblbc.login.controller;

import cn.lblbc.login.bean.Role;
import cn.lblbc.login.bean.UserDetail;
import cn.lblbc.login.bean.response.LoginResp;
import cn.lblbc.login.bean.response.Resp;
import cn.lblbc.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public Resp<LoginResp> login(@RequestBody UserDetail user) {
        return authService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping(value = "/register")
    public Resp<LoginResp> register(@RequestBody UserDetail user) {
        long defaultRoleId = 1L;
        UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(), new Role(defaultRoleId));
        return authService.register(userDetail);
    }

}
