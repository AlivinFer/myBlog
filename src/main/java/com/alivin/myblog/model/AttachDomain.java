package com.alivin.myblog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网站图片文件相关
 *
 * @author Fer
 * date 2021/8/30
 */
@Data
public class AttachDomain {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("文件名称")
    private String fname;

    @ApiModelProperty("文件类型")
    private String ftype;

    @ApiModelProperty("文件地址")
    private String fkey;

    @ApiModelProperty("创建人ID")
    private Integer authorId;

    @ApiModelProperty("创建的时间戳")
    private Integer created;

}
