package com.alivin.myblog.service.user;

import com.alivin.myblog.model.UserDomain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


/**
 * @author Fer
 * @date 2021/8/14
 */

public interface UserService {

    // 更新用戶信息
    int updateUserInfo(UserDomain user);

    // 根据用户名和密码获取用户信息
    UserDomain login(@Param("username") String username,
                  @Param("password") String password);

    // 通过主键查找用户
    UserDomain getUserInfoById(Integer id);
}

