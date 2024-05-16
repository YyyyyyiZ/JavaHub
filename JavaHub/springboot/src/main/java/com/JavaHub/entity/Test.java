package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "test")
public class Test {

    @TableId(value = "testid",type = IdType.AUTO)
    private Integer testid;
    private String userid;
    private Integer timecost;
    private Integer score;
    private String testdate;
    private String chapid;
    private String remark;
}
