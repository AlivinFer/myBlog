package com.alivin.myblog.interceptor;

import com.alivin.myblog.constant.Types;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.model.OptionsDomain;
import com.alivin.myblog.model.UserDomain;
import com.alivin.myblog.service.option.OptionService;
import com.alivin.myblog.service.user.UserService;
import com.alivin.myblog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义拦截器
 *
 * @author Fer
 * date 2021/8/20
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user_agent";

    @Autowired
    private UserService userService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    @Autowired
    private OptionService optionService;

    private MapCache cache = MapCache.single();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        LOGGER.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGER.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIPAddrByRequest(request));

        //请求拦截处理
        UserDomain user = TaleUtils.getLoginUser(request);
        if (null == user) {
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                user = userService.getUserInfoById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            }
        }
            if (uri.startsWith("/admin") && (!uri.startsWith("/admin/login")) && (null == user)
                    && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/images")
                    && !uri.startsWith("/admin/js") && !uri.startsWith("/admin/plugins")
                    && !uri.startsWith("/admin/editormd")) {
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return false;
            }
            //设置get请求的token
            if (request.getMethod().equals("GET")) {
                String csrf_token = UUID.UU64();
                // 默认存储30分钟
                cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
                request.setAttribute("_csrf_token", csrf_token);
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //一些工具类和公共方法
        OptionsDomain ov = optionService.getOptionByName("site_record");
        request.setAttribute("commons", commons);
        request.setAttribute("option", ov);
        request.setAttribute("adminCommons", adminCommons);
        initSiteConfig(request);
    }

    private void initSiteConfig(HttpServletRequest request) {
        if (WebConst.initConfig.isEmpty()){
            List<OptionsDomain> options = optionService.getOptions();
            Map<String, String> queries = new HashMap<>();
            options.forEach(option -> {
                queries.put(option.getName(), option.getValue());
            });
            WebConst.initConfig = queries;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}