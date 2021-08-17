package com.alivin.myblog.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


/**
 * 国际化
 *
 * @author Fer
 * @date 2021/8/16
 */


public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        // 获取请求中的参数
        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault(); // 没有，则走默认的

        // 如果请求中携带了国际化的参数
        if (!StringUtils.isEmpty(language)) {
            String[] split = language.split("_");
            // 国家、地区
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
