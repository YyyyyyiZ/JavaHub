package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "note")
public class Note {

    @TableId(value = "noteid",type = IdType.AUTO)
    private Integer noteid;
    private String userid;
    private String secid;
    private String remark;
}
