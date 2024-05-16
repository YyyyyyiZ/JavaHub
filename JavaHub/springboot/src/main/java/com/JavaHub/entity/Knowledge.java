package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "knowledge")
public class Knowledge {

    @TableId(value = "knowid",type = IdType.AUTO)
    private Integer knowid;
    private String knowpoint;
    private String knowdetail;
    private String secid;
}
