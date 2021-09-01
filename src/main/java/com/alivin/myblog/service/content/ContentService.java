package com.alivin.myblog.service.content;

import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.model.ContentDomain;
import com.github.pagehelper.PageInfo;

/**
 * 文章内容服务
 *
 * @author Fer
 * date 2021/8/28
 */
public interface ContentService {
    /**
     * 添加文章
     */
    void addArticle(ContentDomain contentDomain);

    /**
     * 根据编号删除文章
     */
    void deleteArticleById(Integer cid);

    /**
     * 更新文章
     */
    void updateArticleById(ContentDomain contentDomain);

    /**
     * 更新分类
     */
    void updateCategory(String ordinal, String newCategory);



    /**
     * 添加文章点击量
     */
    void updateContentByCid(ContentDomain content);

    /**
     * 根据编号获取文章
     */
    ContentDomain getArticleById(Integer cid);

    /**
     * 根据条件获取文章列表
     */
    PageInfo<ContentDomain> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize);

    /**
     * 获取最近的文章（只包含id和title）
     */
    PageInfo<ContentDomain> getRecentlyArticle(int pageNum, int pageSize);

    /**
     * 搜索文章
     */
    PageInfo<ContentDomain> searchArticle(String param, int pageNun, int pageSize);
}
