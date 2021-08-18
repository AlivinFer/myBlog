package com.alivin.myblog.service.api;

import com.alivin.myblog.model.UserDomain;
import org.apache.ibatis.annotations.Param;


/**
 * @author Fer
 * @date 2021/8/14
 */

public interface UserService {

    // 更新用戶信息
    int updateUserInfo(UserDomain user);

    // 通过主键查找用户
    UserDomain selectUserById(Long id);

    // 根据用户名和密码获取用户信息
    UserDomain login(@Param("username") String username,
                  @Param("password") String password);
}

