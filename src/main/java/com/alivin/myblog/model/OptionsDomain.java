package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网站配置
 *
 * @author Fer
 * date 2021/8/20
 */
@Data
public class OptionsDomain {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "内容")
    private String value;

    @ApiModelProperty(value = "备注")
    private String description;
}
