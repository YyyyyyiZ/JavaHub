package com.JavaHub.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Value;

@Data
public class CommentDTO {

    @TableId(value = "commentid",type = IdType.AUTO)
    private Integer commentid;
    private String userid;
    private String comment;
    private String username;
    private String exerid;
}
