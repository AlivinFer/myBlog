package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.controller.BaseController;
import com.alivin.myblog.dto.cond.CommentCond;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.CommentDomain;
import com.alivin.myblog.service.comment.CommentService;
import com.alivin.myblog.utils.APIResponse;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fer
 * date 2021/8/28
 */
@Api(tags = "CommentController", description = "评论相关接口")
@Controller
@RequestMapping("/admin/comments")
public class CommentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @ApiOperation("进入评论列表页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getComment(
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
            int page,
            @ApiParam(name = "limit", value = "每页条数", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "15")
            int limit,
            HttpServletRequest request) {
        PageInfo<CommentDomain> comments = commentService.getCommentsByCond(new CommentCond(), page, limit);
        request.setAttribute("comments", comments);
        return "admin/comment_list";
    }

    @ApiOperation("删除一条评论")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> deleteComment(
            @ApiParam(name = "coid", value = "评论编号", required = true)
            @RequestParam(name = "coid", required = true)
            int coid) {
        try {
            CommentDomain comment = commentService.getCommentById(coid);
            if (null == comment) {
                throw BusinessException.withErrorCode(ErrorConstant.Comment.COMMENT_NOT_EXIST);
            }
            commentService.deleteComment(coid);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }

    @ApiOperation("更改评论状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    @ResponseBody
    public <T> APIResponse<T> changeStatus(
            @ApiParam(name = "coid", value = "评论主键", required = true)
            @RequestParam(name = "coid", required = true)
            int coid,
            @ApiParam(name = "status", value = "状态", required = true)
            @RequestParam(name = "status", required = true)
            String status) {
        try {
            CommentDomain comment = commentService.getCommentById(coid);
            if (null != comment) {
                commentService.updateCommentStatus(coid, status);
            } else {
                return APIResponse.fail("更新评论失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return APIResponse.fail(e.getMessage());
        }
        return APIResponse.success();
    }
}
