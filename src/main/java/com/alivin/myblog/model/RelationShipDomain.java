package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章关联信息表
 *
 * @author Fer
 * date 2021/8/23
 */
@Data
public class RelationShipDomain implements Serializable {

    @ApiModelProperty("文章主键编号")
    private Integer cid;

    @ApiModelProperty("项目编号")
    private Integer mid;
}
