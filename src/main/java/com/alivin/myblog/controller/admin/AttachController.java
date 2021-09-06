package com.alivin.myblog.controller.admin;

import com.alivin.myblog.config.QiNiuConfig;
import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.constant.Types;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.AttachDomain;
import com.alivin.myblog.model.UserDomain;
import com.alivin.myblog.service.attach.AttachService;
import com.alivin.myblog.utils.APIResponse;
import com.alivin.myblog.utils.Commons;
import com.alivin.myblog.utils.TaleUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Fer
 * date 2021/8/30
 */
@Api(tags = "AttachController", description = "附件相关接口")
@Controller
@RequestMapping("/admin/attach")
public class AttachController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = TaleUtils.getUploadFilePath();


    @Autowired
    private QiNiuConfig qiNiuConfig;

    @Autowired
    private AttachService attachService;


    @ApiOperation("文件管理首页")
    @RequestMapping(value = "")
    public String index(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
                    int page,
            @ApiParam(name = "limit", value = "条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "12")
                    int limit,
            HttpServletRequest request
    ) {
        PageInfo<AttachDomain> attaches = attachService.getAllAttaches(page, limit);
        request.setAttribute("attaches", attaches);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }

    @ApiOperation("markdown 文件上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void fileUploadToQiniuCloud(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @ApiParam(name = "editormd-image-file", value = "文件数组", required = true)
                                       @RequestParam(name = "editormd-image-file", required = true)
                                       MultipartFile file) {
        // 文件上传
        try {
            request.setCharacterEncoding( "utf-8" );
            response.setHeader( "Content-Type" , "text/html" );

            AttachDomain attach = new AttachDomain();
            String fileName = TaleUtils.getFileKey(file.getOriginalFilename()).replaceFirst("/","");
            qiNiuConfig.upload(file, fileName);
            HttpSession session = request.getSession();
            UserDomain sessionUser = (UserDomain)session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            attach.setAuthorId(sessionUser.getUid());
            attach.setFtype(TaleUtils.isImage(file.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType());
            attach.setFname(fileName);
            String baseUrl = qiNiuConfig.PATH.endsWith("/") ? qiNiuConfig.PATH : qiNiuConfig.PATH + "/";
            attach.setFkey(baseUrl + fileName);
            attachService.addAttAch(attach);
            response.getWriter().write("{\"success\": 1, \"message\":\"上传成功\",\"url\":\"" + attach.getFkey() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.getWriter().write( "{\"success\":0}" );
            } catch (IOException e1) {
                throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                        .withErrorMessageArguments(e.getMessage());
            }
            throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }

    }


    @ApiOperation("多文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> filesUploadToCloud(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 @ApiParam(name = "file", value = "文件数组", required = true)
                                                 @RequestParam(name = "file", required = true)
                                                 MultipartFile[] files){
        //文件上传
        try {
            request.setCharacterEncoding( "utf-8" );
            response.setHeader( "Content-Type" , "text/html" );

            for (MultipartFile file : files) {
                String fileName = TaleUtils.getFileKey(file.getOriginalFilename()).replaceFirst("/","");

                qiNiuConfig.upload(file, fileName);
                AttachDomain attach = new AttachDomain();
                HttpSession session = request.getSession();
                UserDomain sessionUser = (UserDomain)session.getAttribute(WebConst.LOGIN_SESSION_KEY);
                attach.setAuthorId(sessionUser.getUid());
                attach.setFtype(TaleUtils.isImage(file.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType());
                attach.setFname(fileName);
                String baseUrl = qiNiuConfig.PATH.endsWith("/") ? qiNiuConfig.PATH : qiNiuConfig.PATH + "/";
                attach.setFkey(baseUrl + fileName);
                attachService.addAttAch(attach);
            }
            return APIResponse.success();
        } catch (IOException e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(ErrorConstant.Att.UPLOAD_FILE_FAIL)
                    .withErrorMessageArguments(e.getMessage());
        }
    }

    @ApiOperation("删除文件记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> deleteFileInfo(
            @ApiParam(name = "id", value = "文件主键", required = true)
            @RequestParam(name = "id", required = true)
            Integer id,
            HttpServletRequest request
    ){
        try {
            AttachDomain attAch = attachService.getAttachById(id);
            if (null == attAch)
                throw BusinessException.withErrorCode(ErrorConstant.Att.DELETE_ATT_FAIL +  ": 文件不存在");
            attachService.deleteAttAch(id);
            return APIResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw BusinessException.withErrorCode(e.getMessage());
        }
    }
}
