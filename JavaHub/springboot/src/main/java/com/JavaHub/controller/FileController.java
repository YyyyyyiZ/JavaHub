package com.JavaHub.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.JavaHub.common.Result;
import com.JavaHub.entity.Image;
import com.JavaHub.mapper.FileMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.util.List;

/**
 * 学习模块文件上传接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/section")
    public Result findPage(@RequestBody Image image){
        String userid=image.getUserid();
        String secid=image.getSecid();
        List<Image> filesList = fileMapper.selectFile(userid,secid);
        return Result.success(filesList);
    }

    @PostMapping("/uploadURL")
    public void uploadURL(@RequestBody Image image){
        fileMapper.insert(image);
    }


    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,
                         @RequestParam String userid,
                         @RequestParam String secid
    ) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        File parentFile = uploadFile.getParentFile();
        String md5 = SecureUtil.md5(file.getInputStream());
        String url;

        if (!parentFile.exists()){
            parentFile.mkdirs();
        }

        Image dbFiles = getFileMd5(md5, userid, secid);

        if (dbFiles != null){
            url = dbFiles.getUrl();
        } else {
            //存储到磁盘
            file.transferTo(uploadFile);
            url = "userImg/" + fileUUID;
            //保存到数据库
            Image savefile = new Image();
            savefile.setFilename(originalFilename);
            savefile.setSecid(secid);
            savefile.setMd5(md5);
            savefile.setUserid(userid);
            savefile.setUrl(url);
            fileMapper.insert(savefile);
        }
        return url;
    }

    //下载图片
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException{

        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Image image){
        String userid = image.getUserid();
        String secid = image.getSecid();
        String url = image.getUrl();
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("secid",secid);
        queryWrapper.eq("url",url);
        fileMapper.delete(queryWrapper);
        return Result.success(fileMapper.delete(queryWrapper));
    }

    private Image getFileMd5(String md5, String userid, String secid){
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("secid",secid);
        List<Image> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0?null : filesList.get(0);
    }
}
