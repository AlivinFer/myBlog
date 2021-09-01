package com.alivin.myblog.dao;

import com.alivin.myblog.dto.ArchiveDto;
import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.model.ContentDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/28
 */
@Mapper
@Repository
public interface ContentDao {
    /**
     * 添加文章
     */
    int addArticle(ContentDomain contentDomain);

    /**
     * 根据编号删除文章
     */
    int deleteArticleById(@Param("cid") Integer cid);

    /**
     * 更新文章
     */
    int updateArticleById(ContentDomain contentDomain);

    /**
     * 更新文章的评论数
     */
    int updateArticleCommentCountById(@Param("cid") Integer cid, @Param("commentsNum") Integer commentsNum);

    /**
     * 根据编号获取文章

     */
    ContentDomain getArticleById(@Param("cid") Integer cid);

    /**
     * 根据条件获取文章列表
     */
    List<ContentDomain> getArticlesByCond(ContentCond contentCond);

    /**
     * 获取文章总数量
     */
    Long getArticleCount();

    /**
     * 获取归档数据
     * @param contentCond 查询条件（只包含开始时间和结束时间）
     */
    List<ArchiveDto> getArchive(ContentCond contentCond);

    /**
     * 获取最近的文章（只包含id和title）
     */
    List<ContentDomain> getRecentlyArticle();

    /**
     * 搜索文章-根据标题 或 内容匹配
     */
    List<ContentDomain> searchArticle(@Param("param") String param);

}
