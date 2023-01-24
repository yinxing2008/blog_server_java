package cn.lblbc.blog.mapper;

import cn.lblbc.blog.bean.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
@Repository
public interface BlogMapper {
    @Select(value = "select * from blog where id = #{blogId}")
    Blog query(@Param("blogId") long blogId);

    @Select(value = "select * from blog where user_id = #{userId}")
    List<Blog> queryByUserId(@Param("userId") long userId);

    @Insert(value = "insert into blog(user_id,title,content) values (#{userId}, #{title}, #{content})")
    void add(@Param("userId") long userId, @Param("title") String title, @Param("content") String content);

    @Update(value = "update blog set title=#{title},content=#{content} where id = #{id}")
    void modify(@Param("id") long id, @Param("title") String title, @Param("content") String content);

    @Delete("delete from blog where id = #{id}")
    void del(@Param("id") long id);
}
