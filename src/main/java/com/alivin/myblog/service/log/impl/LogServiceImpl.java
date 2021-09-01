package com.alivin.myblog.service.log.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.dao.LogDao;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.LogDomain;
import com.alivin.myblog.service.log.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志实现类
 *
 * @author Fer
 * date 2021/8/23
 */
@Service
public class LogServiceImpl implements LogService{

    @Autowired
    private LogDao logDao;

    @Override
    public int addLog(String action, String data, String ip, Integer authorId) {
        LogDomain log = new LogDomain();
        log.setAction(action);
        log.setData(data);
        log.setIp(ip);
        log.setAuthorId(authorId);
        return logDao.addLog(log);
    }

    @Override
    public int deleteLog(Integer id) {
        if (null == id)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return logDao.deleteLogById(id);
    }

    @Override
    public PageInfo<LogDomain> getLogs(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LogDomain> logs = logDao.getLogs();
        return new PageInfo<>(logs);
    }
}
