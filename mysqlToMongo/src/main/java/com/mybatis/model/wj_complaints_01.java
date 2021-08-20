package com.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class wj_complaints_01 {
    private Integer id;

    private String status;

    private String sourceurl;

    private String title;

    private String content;

    private String punishdate;

    private String reply;

    private String label1;

    private String label2;

    private String respondent;

    private String replytime;

    private String number;

    private String lettertype;
}