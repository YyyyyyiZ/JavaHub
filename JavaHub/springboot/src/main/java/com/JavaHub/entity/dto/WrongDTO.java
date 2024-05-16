package com.JavaHub.entity.dto;

import lombok.Data;

@Data
public class WrongDTO {
    private Integer wrongid;
    private String exerques;
    private String[] choice;
    private String[] answer;
    private String exerans;
    private String exertype;
    private String knowpoint;
    private String remarks;
    private Integer exerid;

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
