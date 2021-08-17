package com.alivin.myblog.dao;

import com.alivin.myblog.mbg.model.MbAdmin;
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

  /*  // 获取所有用户信息
    public List<MbAdmin> getUserInfo();

    // 根据 id 获取用户信息
    public MbAdmin getUserInfoById(@Param("id") Long id);

    // 更新用户信息
    public int updateUserInfo(MbAdmin mbAdmin);*/

    // 根据用户名和密码获取用户信息
    public MbAdmin getUserInfoByCond(@Param("username") String username, @Param("password") String password);
}
