package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.LogActions;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.controller.BaseController;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.UserDomain;
import com.alivin.myblog.service.log.LogService;
import com.alivin.myblog.service.user.UserService;
import com.alivin.myblog.utils.APIResponse;
import com.alivin.myblog.utils.IPKit;
import com.alivin.myblog.utils.MapCache;
import com.alivin.myblog.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录页
 *
 * @author Fer
 * @date 2021/8/13
 */
@Api(tags = "LoginController", description = "登录相关接口")
@Controller
@RequestMapping(value = "/admin")
public class LoginController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    protected MapCache cache = MapCache.single();

    @ApiOperation("跳转登录页")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/admin/login";
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> toLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "username", value = "用户名", required = true)
            @RequestParam(name = "username", required = true)
                    String username,
            @ApiParam(name = "password", value = "密码", required = true)
            @RequestParam(value = "password", required = true)
                    String password,
            @ApiParam(name = "rememberMe", value = "记住我")
            @RequestParam(value = "rememberMe", required = false)
                    String rememberMe
    ) {
        // 获取ip并过滤登录时缓存的bug
        String ip = IPKit.getIPAddrByRequest(request);
        Integer error_count = cache.hget("login_error_count",ip);
        try {
            UserDomain userInfo = userService.login(username, password);
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userInfo);
            if (StringUtils.isNotBlank(rememberMe)) {
                TaleUtils.setCookie(response, userInfo.getUid());
            }
            logService.addLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), userInfo.getUid());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            error_count = null == error_count ? 1 : error_count + 1;
            if (error_count > 3) {
                return APIResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
            }
            cache.hset("login_error_count", ip,error_count, 10 * 60); // 加入ip的过滤
            String msg = "登录失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return APIResponse.fail(msg);
        }
        return APIResponse.success();
    }

    @ApiOperation("注销")
    @RequestMapping(value = "/logout")
    @ResponseBody
    public void logout(HttpServletResponse response, HttpSession session) {
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        // 立即销毁cookie
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("注销失败", e);
        }
    }
}
