package cn.lblbc.login.mapper;

import cn.lblbc.login.bean.Role;
import cn.lblbc.login.bean.UserDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
@Repository
public interface AuthMapper {
    /**
     * 根据用户名查找用户
     */
    @Select("select id, name, password from sys_user where name = #{name}")
    UserDetail findUserByName(@Param("name") String name);

    /**
     * 创建新用户
     */
    @Insert("insert into sys_user (name, password) VALUES (#{name}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserDetail user);

    /**
     * 创建用户角色
     */
    @Insert("insert into sys_user_role (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insertRole(@Param("userId") long userId, @Param("roleId") long roleId);

    /**
     * 根据用户id查找该用户角色
     */
    @Select("select * from sys_role where id in (SELECT role_id from sys_user_role where user_id = #{userId})")
    Role findRoleByUserId(@Param("userId") long userId);
}
