package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "wrongset")
public class Wrongset {

    @TableId(value = "wrongid",type = IdType.AUTO)
    private Integer wrongid;
    private String userid;
    private Integer exerid;
    private String remarks;

}
