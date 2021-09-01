package com.alivin.myblog.dto;

import com.alivin.myblog.model.ContentDomain;
import lombok.Data;

import java.util.List;

/**
 * 文章归档类
 *
 * @author Fer
 * date 2021/8/28
 */
@Data
public class ArchiveDto {

    private String date;
    private String count;
    private List<ContentDomain> articles;
}
