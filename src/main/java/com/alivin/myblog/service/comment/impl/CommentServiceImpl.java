package com.alivin.myblog.service.comment.impl;

import com.alivin.myblog.dao.CommentDao;
import com.alivin.myblog.dto.cond.CommentCond;
import com.alivin.myblog.model.CommentDomain;
import com.alivin.myblog.service.comment.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/28
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;


    @Override
    public void addComment(CommentDomain commentDomain) {

    }

    @Override
    public void deleteComment(Integer coid) {

    }

    @Override
    public void updateCommentStatus(Integer coid, String status) {

    }

    @Override
    public CommentDomain getCommentById(Integer coid) {
        return null;
    }

    @Override
    public List<CommentDomain> getCommentsByCId(Integer cid) {
        return null;
    }

    @Override
    public PageInfo<CommentDomain> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize) {
        return null;
    }
}
