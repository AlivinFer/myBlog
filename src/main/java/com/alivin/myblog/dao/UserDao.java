package com.alivin.myblog.dao;

import com.alivin.myblog.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Fer
 * @date 2021/8/13
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * 更新用户信息
     *
     * @param user 用户
     */
    int updateUserInfo(UserDomain user);

    /**
     * 根据主键编号获取用户信息
     * @param id 用户 id
     */
    UserDomain getUserInfoById(@Param("id") Long id);

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username 用户名
     * @param password 密码
     */
    UserDomain getUserInfoByCond(@Param("username") String username, @Param("password") String password);
}
