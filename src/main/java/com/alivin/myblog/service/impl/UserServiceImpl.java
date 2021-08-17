package com.alivin.myblog.service.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.dao.UserDao;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.mbg.mapper.MbAdminMapper;
import com.alivin.myblog.mbg.model.MbAdmin;
import com.alivin.myblog.service.api.UserService;
import com.alivin.myblog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Fer
 * @date 2021/8/14
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MbAdminMapper adminMapper;

    @Override
    public int updateByPrimaryKey(MbAdmin record) {
        if (null == record.getId()) {
            throw BusinessException.withErrorCode("用户编号不可能为空");
        }
        return adminMapper.updateByPrimaryKey(record);
    }

    @Override
    public MbAdmin selectByPrimaryKey(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public MbAdmin login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);
        }
        String pwd = TaleUtils.MD5encode(username + password);
        MbAdmin user = userDao.getUserInfoByCond(username, pwd);
        if (null == user)
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);
        return user;
    }
}

