package com.alivin.myblog.service.attach;

import com.alivin.myblog.model.AttachDomain;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/31
 */
public interface AttachService {

    /**
     * 添加单个附件信息
     */
    void addAttAch(AttachDomain attachDomain);

    /**
     * 批量添加附件信息
     */
    void batchAddAttAch(List<AttachDomain> attaches);

    /**
     * 根据主键编号删除附件信息
     */
    void deleteAttAch(Integer id);

    /**
     * 更新附件信息
     */
    void updateAttAch(AttachDomain attach);

    /**
     * 根据主键获取附件信息
     */
    AttachDomain getAttachById(Integer id);

    /**
     * 获取所有的附件信息
     */
    PageInfo<AttachDomain> getAllAttaches(int pageNum, int pageSize);
}
