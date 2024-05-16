package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "section")
public class Section {
    @TableId(value = "secid",type = IdType.AUTO)
    private String secid;
    private String secname;
    private String chapid;
}
