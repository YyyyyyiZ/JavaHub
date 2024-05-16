package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@TableName(value = "user")
@ToString
public class User {

    @TableId(value = "userid",type = IdType.AUTO)
    private String userid;
    private String username;
    private String password;
    private String gender;
    private String school;
    private Integer grade;
    private String verifyques;
    private String verifyans;
}
