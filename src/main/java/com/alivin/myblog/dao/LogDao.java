package com.alivin.myblog.dao;

import com.alivin.myblog.model.LogDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日志
 *
 * @author Fer
 * date 2021/8/22
 */
@Mapper
@Repository
public interface LogDao {

    /**
     * 添加日志
     */
    int addLog(LogDomain logDomain);

    /**
     * 删除日志
     */
    int deleteLogById(@Param("id") Integer id);

    /**
     * 获取日志
     */
    List<LogDomain> getLogs();
}
