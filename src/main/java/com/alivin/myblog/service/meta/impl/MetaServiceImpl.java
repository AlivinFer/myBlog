package com.alivin.myblog.service.meta.impl;

import com.alivin.myblog.dao.MetaDao;
import com.alivin.myblog.dao.RelationShipDao;
import com.alivin.myblog.dto.MetaDto;
import com.alivin.myblog.dto.cond.MetaCond;
import com.alivin.myblog.model.MetaDomain;
import com.alivin.myblog.service.meta.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fer
 * date 2021/8/23
 */
@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private RelationShipDao relationshipDao;

    @Override
    public void addMeta(MetaDomain meta) {

    }

    @Override
    public void saveMeta(String type, String name, Integer mid) {

    }

    @Override
    public void addMetas(Integer cid, String names, String type) {

    }

    @Override
    public void saveOrUpdate(Integer cid, String name, String type) {

    }

    @Override
    public void deleteMetaById(Integer mid) {

    }

    @Override
    public void updateMeta(MetaDomain meta) {

    }

    @Override
    public MetaDomain getMetaById(Integer mid) {
        return null;
    }

    @Override
    public List<MetaDomain> getMetas(MetaCond metaCond) {
        return null;
    }

    @Override
    public List<MetaDto> getMetaList(String type, String orderBy, int limit) {
        return null;
    }
}
