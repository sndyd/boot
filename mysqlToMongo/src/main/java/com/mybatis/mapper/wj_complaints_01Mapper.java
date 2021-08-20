package com.mybatis.mapper;

import com.mybatis.model.wj_complaints_01;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface wj_complaints_01Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(wj_complaints_01 record);

    int insertSelective(wj_complaints_01 record);

    wj_complaints_01 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(wj_complaints_01 record);

    int updateByPrimaryKey(wj_complaints_01 record);

    List<wj_complaints_01> selectAll();
}