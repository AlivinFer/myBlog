package com.alivin.myblog.service.option.impl;

import com.alivin.myblog.constant.ErrorConstant;
import com.alivin.myblog.dao.OptionsDao;
import com.alivin.myblog.exception.BusinessException;
import com.alivin.myblog.model.OptionsDomain;
import com.alivin.myblog.service.option.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 网站配置实现
 *
 * @author Fer
 * date 2021/8/24
 */
@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionsDao optionsDao;

    @Override
    public int addOption(OptionsDomain option) {
        return optionsDao.addOption(option);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"optionsCache", "optionCache"}, allEntries = true, beforeInvocation = true)
    public int updateOptionByName(String name, String value) {
        if (StringUtils.isBlank(name)) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        OptionsDomain option = new OptionsDomain();
        option.setName(name);
        option.setValue(value);
        return optionsDao.updateOptionByName(name);
    }

    @Override
    @CacheEvict(value={"optionsCache","optionCache"},allEntries=true,beforeInvocation=true)
    public int deleteOptionByName(String name) {
        if (StringUtils.isBlank(name)) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        return optionsDao.deleteOptionByName(name);
    }

    @Override
    // #p0 指加有@Cacheable注解的方法中的第一个参数
    @Cacheable(value = "optionCache",key = "'optionByName_' + #p0")
    public OptionsDomain getOptionByName(String name) {
        if (StringUtils.isBlank(name)) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        return optionsDao.getOptionByName(name);
    }

    @Override
    @Cacheable(value = "optionsCache", key = "'options_'")
    public List<OptionsDomain> getOptions() {
        return optionsDao.getOptions();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"optionCache", "optionCache"}, allEntries = true, beforeInvocation = true)
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            // :: 简化，表示为调用该方法
            options.forEach(this::updateOptionByName);
        }
    }
}
