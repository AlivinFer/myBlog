package com.alivin.myblog.service.attach.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.dao.AttachDao;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.AttachDomain;
import com.alivin.myblog.service.attach.AttachService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件实现层
 *
 * @author Fer
 * date 2021/8/31
 */
@Service
public class AttachServiceImpl implements AttachService {

    @Autowired
    private AttachDao attachDao;

    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void addAttAch(AttachDomain attachDomain) {
        if (null == attachDomain) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        attachDao.addAttach(attachDomain);
    }

    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void batchAddAttAch(List<AttachDomain> attaches) {
        if (null == attaches || attaches.size() == 0) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        attachDao.batchAddAttach(attaches);
    }

    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void deleteAttAch(Integer id) {
        if (null == id) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        attachDao.deleteAttach(id);
    }

    @Override
    @CacheEvict(value={"attCaches","attCache"},allEntries=true,beforeInvocation=true)
    public void updateAttAch(AttachDomain attach) {
        if (null == attach || null == attach.getId()) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        attachDao.updateAttach(attach);
    }

    @Override
    @Cacheable(value = "attCache", key = "'attAchById' + #p0")
    public AttachDomain getAttachById(Integer id) {
        if (null == id) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        return attachDao.getAttachById(id);
    }

    @Override
    public PageInfo<AttachDomain> getAllAttaches(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttachDomain> attaches = attachDao.getAllAttach();
        return new PageInfo<>(attaches);
    }
}
