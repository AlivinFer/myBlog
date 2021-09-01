package com.alivin.myblog.dto.cond;

import lombok.Data;

/**
 * @author Fer
 * date 2021/8/27
 */
@Data
public class CommentCond {
    /**
     * 状态
     */
    private String status;
    /**
     * 开始时间戳
     */
    private Integer startTime;
    /**
     * 结束时间戳
     */
    private Integer endTime;

    /**
     * 父评论编号
     */
    private Integer parent;
}
