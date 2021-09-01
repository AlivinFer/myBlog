package com.alivin.myblog.controller;

import com.alivin.myblog.model.UserDomain;
import com.alivin.myblog.utils.MapCache;
import com.alivin.myblog.utils.TaleUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fer
 * @date 2021/8/17
 */
public abstract class BaseController {

    protected MapCache cache = MapCache.single();

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    /**
     * 获取请求绑定的登录对象
     * @param request
     * @return
     */
    public UserDomain user(HttpServletRequest request) {
        return TaleUtils.getLoginUser(request);
    }

    public Integer getUid(HttpServletRequest request){
        return this.user(request).getUid();
    }

    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    public String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        int var4 = arr.length;

        for (String item : arr) {
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }
}
