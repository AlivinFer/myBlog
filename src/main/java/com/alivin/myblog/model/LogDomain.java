package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志类
 *
 * @author Fer
 * date 2021/8/22
 */
@Data
public class LogDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @ApiModelProperty(value = "日志主键")
    private Integer id;

    @ApiModelProperty(value = "生产的动作")
    private String action;

    @ApiModelProperty(value = "生产的数据")
    private String data;

    @ApiModelProperty(value = "发起人Id")
    private Integer authorId;

    @ApiModelProperty(value = "日志产生的IP")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
