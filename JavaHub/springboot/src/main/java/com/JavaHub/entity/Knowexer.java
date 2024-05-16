package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "knowexer")
public class Knowexer {

    @TableId(value="knowexerid",type=IdType.AUTO)
    private String knowid;
    private Integer exerid;
    private String knowexerid;
}
