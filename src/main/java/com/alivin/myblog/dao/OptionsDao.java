package com.alivin.myblog.dao;

import com.alivin.myblog.model.OptionsDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 网站配置
 * @author Fer
 * date 2021/8/24
 */
@Mapper
@Repository
public interface OptionsDao {

    /**
     * 增加网站配置
     */
    int addOption(OptionsDomain optionsDomain);

    /**
     * 根据名称更新网站配置
     */
    int updateOptionByName(@Param("name") String name);

    /**
     * 根据名称删除网站配置
     */
    int deleteOptionByName(@Param("name") String name);

    /**
     * 根据名称获取网站配置
     */
    OptionsDomain getOptionByName(@Param("name") String name);

    /**
     * 获取全部网站配置
     */
    List<OptionsDomain> getOptions();
}
