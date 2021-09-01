package com.alivin.myblog.dao;

import com.alivin.myblog.model.AttachDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/31
 */
@Mapper
@Repository
public interface AttachDao {

    /**
     * 添加单个附件
     */
    int addAttach(AttachDomain attach);

    /**
     * 添加多个附件
     */
    int batchAddAttach(List<AttachDomain> attaches);

    /**
     * 根据 id 删除附件
     */
    int deleteAttach(@Param("id") Integer id);

    /**
     * 更新附件信息
     */
    int updateAttach(AttachDomain attach);

    /**
     * 根据 id 获取附件
     */
    AttachDomain getAttachById(@Param("id") Integer id);

    /**
     * 获取所有附件
     */
    List<AttachDomain> getAllAttach();

    /**
     * 查找附件的数量
     */
    Long countAttaches();
}
