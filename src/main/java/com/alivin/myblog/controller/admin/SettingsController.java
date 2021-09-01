package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.LogActions;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.controller.BaseController;
import com.alivin.myblog.model.OptionsDomain;
import com.alivin.myblog.service.log.LogService;
import com.alivin.myblog.service.option.OptionService;
import com.alivin.myblog.utils.APIResponse;
import com.alivin.myblog.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fer
 * date 2021/8/24
 */
@Api(tags = "SettingsController", description = "系统设置")
@Controller
@RequestMapping("/admin/setting")
public class SettingsController extends BaseController {

    @Autowired
    private OptionService optionService;

    @Autowired
    private LogService logService;

    @ApiOperation("进入设置页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String setting(HttpServletRequest request){
        List<OptionsDomain> optionsList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        optionsList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        request.setAttribute("options", options);
        return "admin/setting";
    }


    @ApiOperation("保存系统设置")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> saveSetting(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> queries = new HashMap<>();
            parameterMap.forEach((key, value) -> {
                queries.put(key, join(value));
            });
            optionService.saveOptions(queries);
            WebConst.initConfig = queries;

            logService.addLog(LogActions.SYS_SETTING.getAction(), GsonUtils.toJsonString(queries), request.getRemoteAddr(), this.getUid(request));
            return APIResponse.success();
        } catch (Exception e) {
            String msg = "保存设置失败";
            return APIResponse.fail(e.getMessage());
        }
    }

}
