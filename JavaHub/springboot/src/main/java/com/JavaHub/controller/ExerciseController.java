package com.JavaHub.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.JavaHub.common.Result;
import com.JavaHub.entity.Comment;
import com.JavaHub.entity.Exercise;
import com.JavaHub.entity.Section;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.CommentDTO;
import com.JavaHub.entity.dto.ExerciseDTO;
import com.JavaHub.entity.dto.ExerrecordDTO;
import com.JavaHub.entity.dto.WrongDTO;
import com.JavaHub.mapper.CommentMapper;
import com.JavaHub.mapper.ExerciseMapper;
import com.JavaHub.service.ExerciseService;
import com.JavaHub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterGraphics;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exercise")
@CrossOrigin(origins = "*")
public class ExerciseController {

    @Resource
    private ExerciseMapper exerciseMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Resource
    private ExerciseService exerciseService;


    @PostMapping("/section")
    @ResponseBody
    public Integer[] select(@RequestBody ExerrecordDTO exerrecordDTO){
        String userid = exerrecordDTO.getUserid();
        String secid=exerrecordDTO.getSecid();
        String type1="单选题";
        String type2="多选题";
        String type3="判断题";
        String type4="填空题";
        Integer num1=exerciseMapper.selectAll(secid,type1);
        Integer num2=exerciseMapper.selectAll(secid,type2);
        Integer num3=exerciseMapper.selectAll(secid,type3);
        Integer num4=exerciseMapper.selectAll(secid,type4);
        Integer done1=exerciseMapper.selectDone(userid,type1,secid);
        Integer done2=exerciseMapper.selectDone(userid,type2,secid);
        Integer done3=exerciseMapper.selectDone(userid,type3,secid);
        Integer done4=exerciseMapper.selectDone(userid,type4,secid);
        Integer[] complete={num1,done1,num2,done2,num3,done3,num4,done4};
        return complete;
    }

    @PostMapping("/type")
    public List<Exercise> selectSingleCh(@RequestBody ExerciseDTO exerciseDTO){
        String secid=exerciseDTO.getSecid();
        String exertype=exerciseDTO.getExertype();
        List<Exercise> dtoList = exerciseMapper.selectType(secid,exertype);
        for (Exercise dto:dtoList){
            //exerques,choice,answer
            if (dto.getExertype().equals("多选题")){
                dto.split_ques();
                dto.split_ans();
            }
            //exerques,choice,exerans
            if (dto.getExertype().equals("单选题")){
                dto.split_ques();
                System.out.println(dto);
            }//else 判断题、填空题 exerques,exerans
            String[] temp=dto.getCommentid().split("~");
            int len = temp.length;
            int[] idList = new int[len];
            for (int k=0;k<len;k++){
                idList[k]=Integer.parseInt(temp[k]);
            }
            System.out.println(idList);
            List<CommentDTO> commentList= commentMapper.selectComment(idList);
            dto.setComments(commentList);
        }
        return dtoList;
    }

    @PostMapping("/comment")
    public boolean newComment(@RequestBody CommentDTO commentDTO){
        String userid = commentDTO.getUserid();
        String exerid = commentDTO.getExerid();
        String comment = commentDTO.getComment();
        //像评论表中插入新评论，返回commentid
        Comment oneComment=new Comment();
        oneComment.setUserid(userid);
        oneComment.setComment(comment);
        String remark = userid+"a";
        oneComment.setRemark(remark);
        //Integer commentid = commentMapper.newComment(oneComment);
        commentMapper.insert(oneComment);
        Integer commentid = commentMapper.selectInserted(remark);
        String newremark = userid+"b";
        commentMapper.updateCommentRemark(commentid,newremark);
        System.out.println(commentid);
        //更新习题表中的评论
        String ids = commentMapper.selectids(exerid);
        String newids= ids+"~"+commentid.toString();
        return commentMapper.updateids(exerid,newids);
    }

//    数据1
    @GetMapping("/exerad")
    public List<Exercise> find(){
        List<Exercise> exerList=exerciseMapper.all();
        for (Exercise dto:exerList) {
            String temp=dto.getExerques().replace("~","/n");
            dto.setExerques(temp);
            String temp0=dto.getExerans().replace("~","/n");
            dto.setExerans(temp0);
        }
        return exerList;
    }

    //    删除
    @DeleteMapping("/exerad/{exerid}")
    public Result update(@PathVariable String exerid){
        exerciseMapper.deleteByID(exerid);
        return Result.success();
    }

    //    更新
    @PutMapping ("/exerad")
    public Result update(@RequestBody Exercise exercise){
        String temp=exercise.getExerques().replace("/n","~");
        String temp0=exercise.getExerans().replace("/n","~");
        exercise.setExerques(temp);
        exercise.setExerans(temp0);
        exerciseMapper.updateexer(exercise.getExerid(),exercise.getExerques(),exercise.getExertype(),exercise.getExerans());
        return Result.success();
    }

//    新增1
    @PostMapping("/exerad/{exerid}/{exertype}/{exerques}/{exerans}")
    public Result savead(@PathVariable Integer exerid,
                         @PathVariable String exertype,
                         @PathVariable String exerques,
                         @PathVariable String exerans){
        exerciseMapper.insertex(exerid,exertype,exerques,exerans);
        return Result.success();
    }

//    新增2
    @PostMapping("/exerad/{knowid}/{exerid}")
    public Result saveknex(@PathVariable Integer knowid,
                           @PathVariable Integer exerid){
        exerciseMapper.insertknex(knowid,exerid);
        return Result.success();
    }

    @PostMapping("/dashchap")
    @ResponseBody
    public List<Integer> daselectchap(){
        List<Integer> complete = new ArrayList<>();
        Integer num1=exerciseMapper.selectChap(1);
        Integer num2=exerciseMapper.selectChap(2);
        Integer num3=exerciseMapper.selectChap(3);
        Integer num4=exerciseMapper.selectChap(4);
        Integer num5=exerciseMapper.selectChap(5);
        Integer num6=exerciseMapper.selectChap(6);
        Integer num7=exerciseMapper.selectChap(7);
        Integer num8=exerciseMapper.selectChap(8);
        Integer num9=exerciseMapper.selectChap(9);
        Integer num10=exerciseMapper.selectChap(10);
        complete.add(num1);
        complete.add(num2);
        complete.add(num3);
        complete.add(num4);
        complete.add(num5);
        complete.add(num6);
        complete.add(num7);
        complete.add(num8);
        complete.add(num9);
        complete.add(num10);
        return complete;
    }

    //    导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Exercise> list=exerciseMapper.all();
        //导出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.write(list,true);

//        设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename= URLEncoder.encode("习题列表","UTF-8");
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
        List<Exercise> list = reader.readAll(Exercise.class);
        for(Exercise exercise:list){
            exerciseService.saveExercise(exercise);
        }
    }
}
