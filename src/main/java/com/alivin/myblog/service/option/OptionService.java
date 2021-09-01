package com.alivin.myblog.service.option;

import com.alivin.myblog.model.OptionsDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Fer
 * date 2021/8/24
 */
public interface OptionService {

    /**
     * 增加网站配置
     */
    int addOption(OptionsDomain option);

    /**
     * 更新网站配置
     */
    int updateOptionByName(@Param("name") String name, @Param("value") String value);

    /**
     * 删除网站配置
     */
    int deleteOptionByName(String name);

    /***
     * 根据名称获取网站配置
     */
    OptionsDomain getOptionByName(String name);

    /**
     * 获取全部网站配置
     */
    List<OptionsDomain> getOptions();

    /**
     * 保存网站配置
     */
    void saveOptions(Map<String, String> options);
}
