package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Fer
 * date 2021/8/27
 */
@Data
public class CommentDomain {

    @ApiModelProperty("主键")
    private Integer coid;

    @ApiModelProperty("关联字段")
    private Integer cid;

    @ApiModelProperty("unix 时间戳")
    private Integer created;

    @ApiModelProperty("评论作者")
    private String author;

    @ApiModelProperty("评论所属用户id")
    private Integer authorId;

    @ApiModelProperty("评论所属内容作者 id")
    private Integer ownerId;

    @ApiModelProperty("评论者邮件")
    private String mail;

    @ApiModelProperty("评论者网址")
    private String url;

    @ApiModelProperty("评论者 ip 地址")
    private String ip;

    @ApiModelProperty("评论者客户端")
    private String agent;

    @ApiModelProperty("评论类型")
    private String type;

    @ApiModelProperty("评论状态")
    private String status;

    @ApiModelProperty("父级评论")
    private Integer parent;

    @ApiModelProperty("评论内容")
    private String content;

    private static final long serialVersionUID = 1L;
}
