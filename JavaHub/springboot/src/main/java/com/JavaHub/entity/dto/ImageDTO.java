package com.JavaHub.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ImageDTO {

    private MultipartFile file;
    private String filename;
    private String userid;
    private String md5;
    private String url;
    private String secid;
}
