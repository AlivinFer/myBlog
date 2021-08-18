package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息
 *
 * @author Fer
 * @date 2021/8/17
 */
@Data
public class UserDomain {
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value = "主页地址")
    private String homeUrl;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "用户组")
    private String groupName;
}
