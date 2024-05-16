package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "testset")
public class TestSet {

    private Integer testid;
    private Integer exerid;
    private String rightwrong;
    private String collected;
    private String userid;

}
