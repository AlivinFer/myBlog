package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章实体类
 *
 * @author Fer
 * date 2021/8/23
 */
@Data
public class ContentDomain {

    @ApiModelProperty("文章主键")
    private Integer cid;

    @ApiModelProperty("内容标题")
    private String title;

    @ApiModelProperty("标题图片")
    private String titlePic;

    @ApiModelProperty("内容缩略名")
    private String slug;

    @ApiModelProperty("内容生成时的GMT unix时间戳")
    private Integer created;

    @ApiModelProperty("内容更改时的GMT unix时间戳")
    private Integer modified;

    @ApiModelProperty("内容文字")
    private String content;

    @ApiModelProperty("内容所属用户id")
    private Integer authorId;

    @ApiModelProperty("内容类别")
    private String type;

    @ApiModelProperty("内容状态")
    private String status;

    @ApiModelProperty("标签列表")
    private String tags;

    @ApiModelProperty("分类列表")
    private String categories;

    @ApiModelProperty("点击次数")
    private Integer hits;

    @ApiModelProperty("内容所属评论数")
    private Integer commentsNum;

    @ApiModelProperty("是否允许评论")
    private Integer allowComment;

    @ApiModelProperty("是否允许ping")
    private Integer allowPing;

    @ApiModelProperty("允许出现在聚合中")
    private Integer allowFeed;
}
