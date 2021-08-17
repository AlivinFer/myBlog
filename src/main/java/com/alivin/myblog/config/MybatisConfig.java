package com.alivin.myblog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fer
 * @date 2021/8/13
 */

@Configuration
@MapperScan({"com.alivin.myblog.mbg.mapper","com.alivin.myblog.dao"})
public class MybatisConfig {
}
