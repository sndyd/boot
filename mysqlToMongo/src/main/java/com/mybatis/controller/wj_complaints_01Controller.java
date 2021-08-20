package com.mybatis.controller;

import com.mybatis.model.wj_complaints_01;
import com.mybatis.service.Wj_complaints_01ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class wj_complaints_01Controller {

    @Autowired
    private Wj_complaints_01ServiceImpl wjComplaints01Service;

    @RequestMapping(value = "/wj")
    public @ResponseBody Object show(){

        List<wj_complaints_01> list = wjComplaints01Service.showAll();

        System.out.println(list);

        for (wj_complaints_01 wjComplaints01:list) {

            System.out.println(wjComplaints01.toString());

        }
        return list;
    }




}
