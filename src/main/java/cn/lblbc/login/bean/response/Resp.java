package cn.lblbc.login.bean.response;

import lombok.Data;

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
@Data
public class Resp<T> {

    private int code;
    private String msg;
    private T data;

    public final static int ERROR = -1;
    public final static int UNAUTHORIZED = 401;

    public Resp() {
    }

    public Resp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
