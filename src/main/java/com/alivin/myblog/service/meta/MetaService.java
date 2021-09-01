package com.alivin.myblog.service.meta;

import com.alivin.myblog.dto.MetaDto;
import com.alivin.myblog.dto.cond.MetaCond;
import com.alivin.myblog.model.MetaDomain;

import java.util.List;

/**
 * 项目
 * @author Fer
 * date 2021/8/23
 */
public interface MetaService {
    /**
     * 添加项目
     */
    void addMeta(MetaDomain meta);

    /**
     * 保存项目
     */
    void saveMeta(String type, String name, Integer mid);


    /**
     * 批量添加
     */
    void addMetas(Integer cid, String names, String type);



    /**
     * 添加或者更新
     */
    void saveOrUpdate(Integer cid, String name, String type);

    /**
     * 删除项目
     */
    void deleteMetaById(Integer mid);

    /**
     * 更新项目
     */
    void updateMeta(MetaDomain meta);

    /**
     * 根据编号获取项目
     */
    MetaDomain getMetaById(Integer mid);

    /**
     * 获取所有的项目
     * @param metaCond 查询条件
     */
    List<MetaDomain> getMetas(MetaCond metaCond);

    /**
     * 根据类型查询项目列表，带项目下面的文章
     */
    List<MetaDto> getMetaList(String type, String orderBy, int limit);
}
