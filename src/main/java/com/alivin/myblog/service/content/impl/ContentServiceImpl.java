package com.alivin.myblog.service.content.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.constant.Types;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.dao.CommentDao;
import com.alivin.myblog.dao.ContentDao;
import com.alivin.myblog.dao.RelationShipDao;
import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.CommentDomain;
import com.alivin.myblog.model.ContentDomain;
import com.alivin.myblog.model.RelationShipDomain;
import com.alivin.myblog.service.content.ContentService;
import com.alivin.myblog.service.meta.MetaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/28
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationShipDao relationShipDao;

    @Transactional
    @Override
    @CacheEvict(value={"articleCache","articleCaches"},allEntries=true,beforeInvocation=true)
    public void addArticle(ContentDomain contentDomain) {
        if (null == contentDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDomain.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDomain.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.addArticle(contentDomain);

        int cid = contentDomain.getCid();
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value={"articleCache","articleCaches"},allEntries=true,beforeInvocation=true)
    public void deleteArticleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentDao.deleteArticleById(cid);
        //同时也要删除该文章下的所有评论
        List<CommentDomain> comments = commentDao.getCommentsByCId(cid);
        if (null != comments && comments.size() > 0){
            comments.forEach(comment ->{
                commentDao.deleteComment(comment.getCoid());
            });
        }
        //删除标签和分类关联
        List<RelationShipDomain> relationShips = relationShipDao.getRelationShipByCid(cid);
        if (null != relationShips && relationShips.size() > 0){
            relationShipDao.deleteRelationShipByCid(cid);
        }

    }

    @Override
    @Transactional
    @CacheEvict(value={"articleCache","articleCaches"},allEntries=true,beforeInvocation=true)
    public void updateArticleById(ContentDomain contentDomain) {
        //标签和分类
        String tags = contentDomain.getTags();
        String categories = contentDomain.getCategories();

        contentDao.updateArticleById(contentDomain);
        int cid = contentDomain.getCid();
        relationShipDao.deleteRelationShipByCid(cid);
        metaService.addMetas(cid, tags, Types.TAG.getType());
        metaService.addMetas(cid, categories, Types.CATEGORY.getType());

    }

    @Override
    @Transactional
    @CacheEvict(value={"articleCache","articleCaches"},allEntries=true,beforeInvocation=true)
    public void updateCategory(String ordinal, String newCategory) {
        ContentCond cond = new ContentCond();
        cond.setCategory(ordinal);
        List<ContentDomain> articles = contentDao.getArticlesByCond(cond);
        articles.forEach(article -> {
            article.setCategories(article.getCategories().replace(ordinal, newCategory));
            contentDao.updateArticleById(article);
        });
    }



    @Override
    @CacheEvict(value={"articleCache","articleCaches"},allEntries=true,beforeInvocation=true)
    public void updateContentByCid(ContentDomain content) {
        if (null != content && null != content.getCid()) {
            contentDao.updateArticleById(content);
        }
    }

    @Override
    @Cacheable(value = "articleCache", key = "'articleById_' + #p0")
    public ContentDomain getArticleById(Integer cid) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentDao.getArticleById(cid);
    }

    @Override
    @Cacheable(value = "articleCaches", key = "'articlesByCond_' + #p1 + 'type_' + #p0.type")
    public PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (null == contentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> contents = contentDao.getArticlesByCond(contentCond);
        return new PageInfo<>(contents);
    }

    @Override
    @Cacheable(value = "articleCaches", key = "'recentlyArticle_' + #p0")
    public PageInfo<ContentDomain> getRecentlyArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentDomain> recentlyArticle = contentDao.getRecentlyArticle();
        return new PageInfo<>(recentlyArticle);
    }

    @Override
    public PageInfo<ContentDomain> searchArticle(String param, int pageNun, int pageSize) {
        PageHelper.startPage(pageNun,pageSize);
        List<ContentDomain> contentDomains = contentDao.searchArticle(param);
        return new PageInfo<>(contentDomains);
    }
}
