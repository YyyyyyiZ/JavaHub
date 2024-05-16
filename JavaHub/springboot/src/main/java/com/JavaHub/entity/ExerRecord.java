package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "exerrecord")
public class ExerRecord {

    @TableId(value = "exerrecordid")
    private Integer exerrecordid;
    private String userid;
    private int exerid;
    private String rightwrong;
    private Integer timecost;
    private Date exerdate;
}
