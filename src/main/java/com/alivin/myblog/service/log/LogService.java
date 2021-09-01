package com.alivin.myblog.service.log;

import com.alivin.myblog.model.LogDomain;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author Fer
 * date 2021/8/23
 */

public interface LogService {

    // 添加日志
    int addLog(@Param("action") String action, @Param("data") String data, @Param("ip") String ip, @Param("authorId") Integer authorId);

    // 删除日志
    int deleteLog(@Param("id") Integer id);

    // 获取日志
    PageInfo<LogDomain> getLogs(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
