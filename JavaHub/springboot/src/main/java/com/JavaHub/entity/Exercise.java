package com.JavaHub.entity;

import com.JavaHub.entity.dto.CommentDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.swing.*;
import java.util.List;

@Data
@TableName(value = "exercise")
public class Exercise {

    @TableId(value = "exerid",type = IdType.AUTO)
    private Integer exerid;
    private String exerques;
    private String exerans;
    private String[] choice;
    private String[] answer;
    private String exertype;
    private Integer dotimes;
    private Integer righttimes;
    private Integer level;
    private String commentid;
    private String knowpoint;
    private int knowid;
    private List<CommentDTO> comments;

    //单选题和多选题
    public void split_ques(){
        choice = new String[4];
        String[] temp = exerques.split("~");
        System.out.println(temp[1]);
        exerques = temp[0];
        for (int i=1;i<temp.length;i++){
            choice[i-1] = temp[i];
        }
        System.out.println(choice[0]);
    }

    //仅针对多选题
    public void split_ans(){
        answer = exerans.split("~");
    }

    //单选题和多选题
    public void merge_ques(){
        int len = choice.length;
        for(int i=0;i<len;i++){
            exerques += choice[i];
        }
    }

    //仅针对多选题
    public void merge_ans(){
        int len = answer.length;
        for(int i=0;i<len;i++){
            exerans += answer[i];
        }
    }

}
