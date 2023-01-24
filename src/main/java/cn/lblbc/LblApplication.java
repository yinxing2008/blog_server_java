package cn.lblbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
@MapperScan({"cn.lblbc.blog.mapper","cn.lblbc.login.mapper"}) //扫描的mapper
@SpringBootApplication
public class LblApplication {

	public static void main(String[] args) {
		SpringApplication.run(LblApplication.class, args);
	}
}
