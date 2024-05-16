package com.JavaHub.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.JavaHub.common.Result;
import com.JavaHub.entity.Knowledge;
import com.JavaHub.entity.Section;
import com.JavaHub.entity.dto.WrongDTO;
import com.JavaHub.mapper.KnowledgeMapper;
import com.JavaHub.mapper.SectionMapper;
import com.JavaHub.service.SectionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/section")
@CrossOrigin(origins = "*")
public class SectionController {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private SectionService sectionService;

    //    新增
    @PostMapping("/secad")
    public Result savead(@RequestBody Section section){
        sectionMapper.insert(section);
        return Result.success();
    }

    //    更新
    @PutMapping ("/secad")
    public Result update(@RequestBody Section section){
        sectionMapper.updateById(section);
        return Result.success();
    }

    //    数据
    @GetMapping("/secad")
    public List<Section> find(){
        return sectionMapper.findAll();
    }

    //    删除
    @DeleteMapping("/secad/{secid}")
    public Result update(@PathVariable String secid){
        sectionMapper.deleteByID(secid);
        return Result.success();
    }

    //    导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Section> list=sectionMapper.findAll();
        //导出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.write(list,true);

//        设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename= URLEncoder.encode("章节列表","UTF-8");
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
        List<Section> list = reader.readAll(Section.class);
        for(Section section:list){
            sectionService.saveSection(section);
        }
    }

}
