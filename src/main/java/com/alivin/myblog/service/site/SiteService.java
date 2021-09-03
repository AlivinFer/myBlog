package com.alivin.myblog.service.site;

import com.alivin.myblog.dto.ArchiveDto;
import com.alivin.myblog.dto.MetaDto;
import com.alivin.myblog.dto.StatisticsDto;
import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.model.CommentDomain;
import com.alivin.myblog.model.ContentDomain;

import java.util.List;

/**
 * 站点服务
 *
 * @author Fer
 * date 2021/9/3
 */
public interface SiteService {

    /**
     * 获取评论列表
     */
    List<CommentDomain> getComments(int limit);

    /**
     * 获取最新的文章
     */
    List<ContentDomain> getNewArticles(int limit);

    /**
     * 获取单条评论
     */
    CommentDomain getComment(Integer coid);

    /**
     * 获取 后台统计数据
     */
    StatisticsDto getStatistics();

    /**
     * 获取归档列表 - 只是获取日期和数量
     */
    List<ArchiveDto> getArchivesSimple(ContentCond contentCond);

    /**
     * 获取归档列表
     * @param contentCond 查询条件（只包含开始时间和结束时间）
     */
    List<ArchiveDto> getArchives(ContentCond contentCond);



    /**
     * 获取分类/标签列表
     */
    List<MetaDto> getMetas(String type, String orderBy, int limit);
}
