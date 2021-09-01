package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.LogActions;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.controller.BaseController;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.UserDomain;
import com.alivin.myblog.service.log.LogService;
import com.alivin.myblog.service.user.UserService;
import com.alivin.myblog.utils.APIResponse;
import com.alivin.myblog.utils.GsonUtils;
import com.alivin.myblog.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import javax.servlet.http.HttpSession;

/**
 * @author Fer
 * @date 2021/8/17
 */
@Api("后台首页")
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @ApiOperation("进入首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        LOGGER.info("Enter admin index method");
        return "admin/index";
    }

    @ApiOperation("个人设置")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        System.out.println("进入个人设置");
        return "admin/profile";
    }

    @ApiOperation("保存个人信息")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> saveProfile(HttpServletRequest request,
                                        HttpSession session,
                                        @RequestParam String nickName,
                                        @RequestParam String email) {
        UserDomain user = this.user(request);
        if (StringUtils.isNotBlank(nickName) && StringUtils.isNotBlank(email)) {
            UserDomain temp = new UserDomain();
            temp.setUid(user.getUid());
            temp.setNickName(nickName);
            temp.setEmail(email);
            int i = userService.updateUserInfo(temp);
            if (i == 1) {
                LOGGER.info("信息更新成功");
            }
            logService.addLog(LogActions.UP_INFO.getAction(), GsonUtils.toJsonString(temp), request.getRequestURI(), this.getUid(request));
            //更新session中的数据
            UserDomain original = (UserDomain) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            original.setNickName(nickName);
            original.setEmail(email);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY, original);
        }
        return APIResponse.success();
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> upPwd(HttpServletRequest request,
                                    HttpSession session,
                                    @RequestParam("oldPassword") String oldPassword,
                                    @RequestParam("password") String password) {
        UserDomain user = this.user(request);
        if (StringUtils.isNotBlank(oldPassword) || StringUtils.isNotBlank(password)){
            return APIResponse.fail("请确认信息输出完整");
        }
        if (!user.getPassword().equals(TaleUtils.MD5encode(user.getUsername() + oldPassword))) {
            return APIResponse.fail("旧密码错误");
        }
        if (password.length() < 6 || password.length() > 14) {
            return APIResponse.fail("请输入6-14位密码");
        }
        try {
            UserDomain temp = new UserDomain();
            temp.setUid(user.getUid());
            String pwd = TaleUtils.MD5encode(user.getUsername() + password);
            temp.setPassword(pwd);
            userService.updateUserInfo(temp);
            logService.addLog(LogActions.UP_PWD.getAction(), null, request.getRequestURI(), this.getUid(request));

            // 更新 session 中数据
            UserDomain original= (UserDomain)session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            original.setPassword(pwd);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY, original);
            return APIResponse.success();
        } catch (Exception e) {
            String msg = "密码修改失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return APIResponse.fail(msg);
        }
    }
}
