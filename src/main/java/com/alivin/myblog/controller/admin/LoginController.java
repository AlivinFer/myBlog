package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.mbg.model.MbAdmin;
import com.alivin.myblog.service.api.UserService;
import com.alivin.myblog.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录页
 *
 * @author Fer
 * @date 2021/8/13
 */
@Api("登录相关接口")
@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @ApiOperation("跳转登录页")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/admin/login";
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public <T> Comparable<T> toLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "username", value = "用户名", required = true)
            @RequestParam(name = "username")
                    String username,
            @ApiParam(name = "password", value = "密码")
            @RequestParam(value = "password")
                    String password,
            @ApiParam(name = "rememberMe", value = "记住我")
            @RequestParam(value = "rememberMe")
                    String rememberMe
    ) {
        try {
            MbAdmin userInfo = userService.login(username, password);
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userInfo);
            if (StringUtils.isNotBlank(rememberMe)) {
                TaleUtils.setCookie(response, userInfo.getId());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());


        }
        return null;
    }
}