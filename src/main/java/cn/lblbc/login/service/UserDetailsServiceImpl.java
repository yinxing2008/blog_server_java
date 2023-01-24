package cn.lblbc.login.service;

import cn.lblbc.login.bean.Role;
import cn.lblbc.login.bean.UserDetail;
import cn.lblbc.login.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登陆身份认证
 * @author 蓝不蓝编程(CSDN、简书、掘金、今日头条、微信公众号、抖音、快手、B站、西瓜视频)
 * 编程学习资料及开源项目见：https://juejin.cn/post/7002792005688360968
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetail userDetail = authMapper.findUserByName(name);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        Role role = authMapper.findRoleByUserId(userDetail.getId());
        userDetail.setRole(role);
        return userDetail;
    }
}
