package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Fer
 * date 2021/8/20
 */
@Data
public class MetaDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目主键")
    private Integer mid;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目缩略名")
    private String slug;

    @ApiModelProperty(value = "项目类型")
    private String type;

    @ApiModelProperty(value = "文章类型")
    private String contentType;

    @ApiModelProperty(value = "选项描述")
    private String description;

    @ApiModelProperty("项目排序")
    private Integer sort;

    private Integer parent;
}
