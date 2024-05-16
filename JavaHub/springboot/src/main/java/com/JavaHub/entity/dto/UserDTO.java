package com.JavaHub.entity.dto;

import lombok.Data;

/**
 *接受前端登录请求的参数
 */
@Data
public class UserDTO {
    private String userid;
    private String password;
    private String verifyques;
    private String verifyans;
    private String username;

}
