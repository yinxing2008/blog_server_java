package cn.lblbc.blog.controller;

import cn.lblbc.blog.bean.Blog;
import cn.lblbc.blog.service.BlogService;
import cn.lblbc.login.bean.response.Resp;
import cn.lblbc.login.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
@RestController
@RequestMapping("/blog")
public class BlogRestController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private JwtUtils jwtUtils;

    @DeleteMapping(value = "/blogs/{id}")
    public Resp<String> deleteBlog(@PathVariable long id) {
        blogService.del(id);
        return new Resp<>();
    }

    @PostMapping(value = "/blogs")
    public Resp<String> addBlog(@RequestBody Blog blog, @RequestHeader("Authorization") String authorization) {
        if (authorization != null) {
            long userId = jwtUtils.getUserIdFromToken(authorization);
            blogService.add(userId,blog.getTitle(), blog.getContent());
        }
        return new Resp<>();
    }

    @PutMapping(value = "/blogs/{id}")
    public Resp<String> modifyBlog(@PathVariable long id,@RequestBody Blog blog) {
        blogService.modify(id, blog.getTitle(), blog.getContent());
        return new Resp<>();
    }

    @GetMapping("/blogs")
    public Resp<List<Blog>> list(@RequestHeader("Authorization") String authorization) {
        Resp<List<Blog>> resp = new Resp<>();
        if (authorization != null) {
            long userId = jwtUtils.getUserIdFromToken(authorization);
            resp.setData(blogService.queryByUserId(userId));
        }
        return resp;
    }

    @GetMapping("/blogs/{blogId}")
    public Resp<Blog> query(@PathVariable long blogId) {
        Resp<Blog> resp = new Resp<>();
        resp.setData(blogService.query(blogId));
        return resp;
    }

}
