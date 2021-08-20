package com.mybatis.service;

import com.mybatis.mapper.wj_complaints_01Mapper;
import com.mybatis.model.wj_complaints_01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Wj_complaints_01ServiceImpl implements Wj_complaints_01Service{

    @Autowired
    private wj_complaints_01Mapper wjComplaints01Mapper;

    @Override
    public List<wj_complaints_01> showAll() {
        return wjComplaints01Mapper.selectAll();
    }

}
