package com.JavaHub.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.JavaHub.common.Result;
import com.JavaHub.entity.Knowledge;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.UserDTO;
import com.JavaHub.entity.dto.WrongDTO;
import com.JavaHub.mapper.KnowledgeMapper;
import com.JavaHub.service.KnowledgeService;
import com.JavaHub.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/knowledge")
@CrossOrigin(origins = "*")
public class KnowledgeController {

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Resource
    private KnowledgeService knowledgeService;

    @PostMapping("/section")
    public List<Knowledge> select(@RequestBody Knowledge knowledge){
        String secid=knowledge.getSecid();
        return knowledgeMapper.select(secid);
    }

    //    新增
    @PostMapping("/knowad")
    public Result savead(@RequestBody Knowledge knowledge){
        knowledgeMapper.insert(knowledge);
        return Result.success();
    }

    //    更新
    @PutMapping ("/knowad")
    public Result update(@RequestBody Knowledge knowledge){
        knowledgeMapper.updateById(knowledge);
        return Result.success();
    }

    //    数据
    @GetMapping("/knowad")
    public List<Knowledge> find(){
        return knowledgeMapper.all();
    }

    //    删除
    @DeleteMapping("/knowad/{knowid}")
    public Result update(@PathVariable String knowid){
        knowledgeMapper.deleteByID(knowid);
        return Result.success();
    }

    //    导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Knowledge> list=knowledgeMapper.all();
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
        String filename= URLEncoder.encode("知识点列表","UTF-8");
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
        List<Knowledge> list = reader.readAll(Knowledge.class);
        for(Knowledge knowledge:list){
            knowledgeService.saveKnowledge(knowledge);
        }
    }

}
