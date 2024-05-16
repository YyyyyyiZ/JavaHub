package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file")
public class Image {


    private String filename;
    private String userid;
    @TableId(value = "url")
    private String url;
    private String md5;
    private String secid;

}
