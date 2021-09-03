package com.alivin.myblog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台统计对象
 *
 * @author Fer
 * date 2021/9/2
 */
@Data
public class StatisticsDto {

    @ApiModelProperty("文章数")
    private Long articles;

    @ApiModelProperty("评论数")
    private Long comments;

    @ApiModelProperty("连接数")
    private Long links;

    @ApiModelProperty("附件数")
    private Long attaches;
}
