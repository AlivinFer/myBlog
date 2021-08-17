package com.alivin.myblog.service.api;

import com.alivin.myblog.mbg.model.MbAdmin;
import org.apache.ibatis.annotations.Param;


/**
 * @author Fer
 * @date 2021/8/14
 */

public interface UserService {

    // 更新用戶信息
    int updateByPrimaryKey(MbAdmin record);

    // 通过主键查找用户
    MbAdmin selectByPrimaryKey(Long id);

    // 根据用户名和密码获取用户信息
    MbAdmin login(@Param("username") String username,
                  @Param("password") String password);
}

