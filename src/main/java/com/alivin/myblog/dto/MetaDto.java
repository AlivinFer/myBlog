package com.alivin.myblog.dto;

import com.alivin.myblog.model.MetaDomain;

/**
 * 标签、分类列表
 *
 * @author Fer
 * date 2021/8/20
 */

public class MetaDto extends MetaDomain {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
