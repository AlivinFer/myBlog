package com.alivin.myblog.utils;

import com.google.gson.Gson;

/**
 * json转换工具
 *
 * @author Fer
 * date 2021/8/23
 */

public class GsonUtils {
    private static final Gson gson = new Gson();

    public static String toJsonString(Object object){
        return object==null?null:gson.toJson(object);
    }
}
