package com.alivin.myblog.service.site.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.constant.Types;
import com.alivin.myblog.constant.WebConst;
import com.alivin.myblog.dao.AttachDao;
import com.alivin.myblog.dao.CommentDao;
import com.alivin.myblog.dao.ContentDao;
import com.alivin.myblog.dao.MetaDao;
import com.alivin.myblog.dto.ArchiveDto;
import com.alivin.myblog.dto.MetaDto;
import com.alivin.myblog.dto.StatisticsDto;
import com.alivin.myblog.dto.cond.CommentCond;
import com.alivin.myblog.dto.cond.ContentCond;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.CommentDomain;
import com.alivin.myblog.model.ContentDomain;
import com.alivin.myblog.service.site.SiteService;
import com.alivin.myblog.utils.DateKit;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fer
 * date 2021/9/3
 */
@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private AttachDao attachDao;

    @Autowired
    private MetaDao metaDao;

    @Override
    @Cacheable(value = "siteCache", key = "'comments_' + #p0")
    public List<CommentDomain> getComments(int limit) {
        LOGGER.debug("Enter recentComments method:limit={}", limit);
        if (limit < 0 || limit > 10){
            limit = 10;
        }
        PageHelper.startPage(1, limit);
        List<CommentDomain> rs = commentDao.getCommentsByCond(new CommentCond());
        LOGGER.debug("Exit recentComments method");
        return null;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'newArticles_' + #p0")
    public List<ContentDomain> getNewArticles(int limit) {
        LOGGER.debug("Enter recentArticles method:limit={}", limit);
        if (limit < 0 || limit > 10)
            limit = 10;
        PageHelper.startPage(1, limit);
        List<ContentDomain> rs = contentDao.getArticlesByCond(new ContentCond());
        LOGGER.debug("Exit recentArticles method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'comment_' + #p0")
    public CommentDomain getComment(Integer coid) {
        LOGGER.debug("Enter recentComment method");
        if (null == coid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        CommentDomain comment = commentDao.getCommentById(coid);
        LOGGER.debug("Exit recentComment method");
        return comment;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'statistics_'")
    public StatisticsDto getStatistics() {
        LOGGER.debug("Enter recentStatistics method");
        //文章总数
        Long articles = contentDao.getArticleCount();

        Long comments = commentDao.getCommentsCount();

        Long links = metaDao.getMetasCountByType(Types.LINK.getType());

        Long attaches = attachDao.countAttaches();

        StatisticsDto rs = new StatisticsDto();
        rs.setArticles(articles);
        rs.setAttaches(attaches);
        rs.setComments(comments);
        rs.setLinks(links);

        LOGGER.debug("Exit recentStatistics method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archivesSimple_' + #p0")
    public List<ArchiveDto> getArchivesSimple(ContentCond contentCond) {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveDto> archives = contentDao.getArchive(contentCond);
        LOGGER.debug("Exit getArchives method");
        return archives;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archives_' + #p0")
    public List<ArchiveDto> getArchives(ContentCond contentCond) {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveDto> archives = contentDao.getArchive(contentCond);
        parseArchives(archives, contentCond);
        LOGGER.debug("Exit getArchives method");
        return archives;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'metas_' + #p0")
    public List<MetaDto> getMetas(String type, String orderBy, int limit) {
        LOGGER.debug("Enter metas method:type={},order={},limit={}", type, orderBy, limit);
        List<MetaDto> retList=null;
        if (StringUtils.isNotBlank(type)) {
            if(StringUtils.isBlank(orderBy)){
                orderBy = "count desc, a.mid desc";
            }
            if(limit < 1 || limit > WebConst.MAX_POSTS){
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("limit", limit);
            retList= metaDao.selectFromSql(paraMap);
        }
        LOGGER.debug("Exit metas method");
        return retList;
    }

    private void parseArchives(List<ArchiveDto> archives, ContentCond contentCond) {
        if (null != archives){
            archives.forEach(archive -> {
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年MM月");
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
                ContentCond cond = new ContentCond();
                cond.setStartTime(start);
                cond.setEndTime(end);
                cond.setType(contentCond.getType());
                List<ContentDomain> contents = contentDao.getArticlesByCond(cond);
                archive.setArticles(contents);
            });
        }
    }
}
