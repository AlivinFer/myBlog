package com.alivin.myblog.dto.cond;

import lombok.Data;

/**
 * 用户查找条件
 *
 * @author Fer
 * date 2021/8/20
 */
@Data
public class UserCond {
    private String username;
    private String password;
}
