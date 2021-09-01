package com.alivin.myblog.controller.admin;

import com.alivin.myblog.constant.Types;
import com.alivin.myblog.controller.BaseController;
import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.dto.cond.MetaCond;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.ContentDomain;
import com.alivin.myblog.model.MetaDomain;
import com.alivin.myblog.service.content.ContentService;
import com.alivin.myblog.service.log.LogService;
import com.alivin.myblog.service.meta.MetaService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章管理
 *
 * @author Fer
 * date 2021/8/23
 */
@Api(tags = "ArticleController", description = "文章管理")
@Controller
@RequestMapping("/admin/article")
@Transactional(rollbackFor = BusinessException.class)
public class ArticleController extends BaseController {
    private static Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private ContentService contentService;

    @ApiOperation("文章页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(
            HttpServletRequest request,
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(name = "page", required = false, defaultValue = "1")
            int page,
            @ApiParam(name = "limit", value = "每页数量", required = false)
            @RequestParam(name = "limit", required = false, defaultValue = "15")
            int limit
    ){
        PageInfo<ContentDomain> articles = contentService.getArticlesByCond(new ContentCond(), page, limit);
        request.setAttribute("articles", articles);
        return "admin/article_list";
    }


    @ApiModelProperty("发布文章页")
    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public String newArticle(HttpServletRequest request) {
        MetaCond metaCond = new MetaCond();
        metaCond.setType(Types.CATEGORY.getType());
        List<MetaDomain> metas = metaService.getMetas(metaCond);
        request.setAttribute("categories", metas);
        return "admin/article_edit";
    }

}
