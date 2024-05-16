package com.JavaHub.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.JavaHub.common.Constants;
import com.JavaHub.common.Result;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.CalendarDTO;
import com.JavaHub.entity.dto.UserDTO;
import com.JavaHub.mapper.CalendarMapper;
import com.JavaHub.mapper.UserMapper;
import com.JavaHub.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/userl")
@CrossOrigin(origins = "*")
public class UserController2 {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

//    做题数
    @RequestMapping ("/count/{userid}/{date}")
    public Integer[] sum(@PathVariable String userid,
                         @PathVariable String[] date){
        Integer exercount[]=new Integer[7];
        for(Integer i=0;i<7;i++){
            exercount[i]=userMapper.countexer(userid,date[i]);
            if(exercount[i]!=null);
            else{
                exercount[i]=0;
            }
        }
        Integer sum=0;
        for(Integer j=0;j<7;j++){
            if(exercount[j].equals(0))
                sum++;
        }
        if(sum==7){
            for(Integer i=0;i<7;i++){
                exercount[i]=userMapper.countex(date[i]);
                if(exercount[i]!=null);
                else{
                    exercount[i]=0;
                }
            }
        }
        return exercount;
    }

//    正确数
    @RequestMapping ("/counttrue/{userid}/{date}")
    public Integer[] sumtrue(@PathVariable String userid,
                             @PathVariable String[] date){
        Integer exercount[]=new Integer[7];
        for(Integer i=0;i<7;i++){
            exercount[i]=userMapper.counttrue(userid,date[i]);
            if(exercount[i]!=null);
            else{
                exercount[i]=0;
            }
        }
        Integer sum=0;
        for(Integer j=0;j<7;j++){
            if(exercount[j].equals(0))
                sum++;
        }
        if(sum==7){
            for(Integer i=0;i<7;i++){
                exercount[i]=userMapper.counttr(date[i]);
                if(exercount[i]!=null);
                else{
                    exercount[i]=0;
                }
            }
        }
        return exercount;
    }

//    薄弱知识
    @PostMapping("/lowknow/{userid}")
    public List<Integer> lowknow(@PathVariable String userid){
        List<Integer> arr=new ArrayList<>();
        arr=userMapper.lowknow(userid);
        return arr;
    }

//    薄弱知识数
    @PostMapping("/lowpoint/{userid}")
    public List<Integer> lowpoint(@PathVariable String userid){
        List<Integer> arr=new ArrayList<>();
        arr=userMapper.lowpoint(userid);
        return arr;
    }

//    薄弱章节
    @PostMapping("/lowsec/{userid}")
    public List<String> lowsec(@PathVariable String userid){
        List<String> arr=userMapper.lowsec(userid);
        return arr;
    }

//    薄弱章节数
    @PostMapping("/lowsecpoint/{userid}")
    public List<Integer> lowsecpoint(@PathVariable String userid){
        List<Integer> arr=userMapper.lowsecpoint(userid);
        return arr;
    }

//    导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list=userMapper.findAll();
        //导出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("userid","ID");
//        writer.addHeaderAlias("username","用户名");
//        writer.addHeaderAlias("gender","性别");
//        writer.addHeaderAlias("school","学校");
//        writer.addHeaderAlias("grade","年级");

        writer.write(list,true);

//        设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+filename+".xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

//    导入
    @PostMapping("/import")
    public void imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        表头与javabean属性对应
        List<User> list = reader.readAll(User.class);
//        List<User> list = reader.read(0,1,User.class);
//        List<List<Object>> list0 = reader.read(1);
//        for(List<Object> row:list0){
//            User user =new User();
//            user.setUserid(row.get(0));
//        }
        for(User user:list){
            userService.saveUser(user);
        }
    }

    @PostMapping("/logadmin")
    public User loginad(){
        User user= userMapper.findfir();
        return user;
    }
}
