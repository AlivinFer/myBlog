package com.alivin.myblog.constant;

/**
 * 各个模块错误的常量
 *
 * @author Fer
 * @date 2021/8/13
 */
public interface ErrorConstant {
    interface Auth {
        static final String USERNAME_PASSWORD_IS_EMPTY = "用户名和密码不可为空";
        static final String USERNAME_PASSWORD_ERROR = "用户名不存在或密码错误";
        static final String NOT_LOGIN = "用户未登录";
    }
}
