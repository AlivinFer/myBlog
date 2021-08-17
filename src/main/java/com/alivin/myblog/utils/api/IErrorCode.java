package com.alivin.myblog.utils.api;

/**
 * 封装 API 的错误码
 *
 * @author Fer
 * @date 2021/8/13
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
