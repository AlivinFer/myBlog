package com.alivin.myblog.utils;

import lombok.Data;

/**
 * 返回的参数封装类
 *
 * @author Fer
 * date 2021/8/20
 */
@Data
public class APIResponse <T> {
    private static final String CODE_SUCCESS = "success";

    private static final String CODE_FAIL = "fail";

    private String code;
    private T data;
    private String msg;

    public APIResponse(){

    }

    public APIResponse(String code){
        this.code = code;
    }

    public APIResponse(String code, T data){
        this.code = code;
        this.data = data;
    }

    public APIResponse(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> APIResponse<T> success(){
        return new APIResponse<T>(CODE_SUCCESS);
    }

    public static <T> APIResponse<T> success(Object data){
        return new APIResponse(CODE_SUCCESS, data);
    }

    public static <T> APIResponse<T> fail(String msg){
        return new APIResponse<T>(CODE_FAIL, msg);
    }

    public static <T> APIResponse<T> widthCode(String errorCode) {
        return new APIResponse<T>(errorCode);
    }
}
