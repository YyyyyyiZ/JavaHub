package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "collecset")
public class Collecset {

    @TableId(value = "collecid",type = IdType.AUTO)
    private Integer collecid;
    private String userid;
    private int exerid;
    private String remarks;
}
