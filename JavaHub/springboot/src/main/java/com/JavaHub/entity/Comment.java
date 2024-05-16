package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(value = "commentid",type = IdType.AUTO)
    private Integer commentid;
    private String userid;
    private String comment;
    private String remark;
}
