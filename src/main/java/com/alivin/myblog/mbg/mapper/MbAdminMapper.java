package com.alivin.myblog.mbg.mapper;

import com.alivin.myblog.mbg.model.MbAdmin;
import com.alivin.myblog.mbg.model.MbAdminExample;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MbAdminMapper {
    long countByExample(MbAdminExample example);

    int deleteByExample(MbAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MbAdmin record);

    int insertSelective(MbAdmin record);

    List<MbAdmin> selectByExample(MbAdminExample example);

    MbAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MbAdmin record, @Param("example") MbAdminExample example);

    int updateByExample(@Param("record") MbAdmin record, @Param("example") MbAdminExample example);

    int updateByPrimaryKeySelective(MbAdmin record);

    int updateByPrimaryKey(MbAdmin record);
}