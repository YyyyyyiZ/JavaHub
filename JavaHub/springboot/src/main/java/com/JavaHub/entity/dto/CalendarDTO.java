package com.JavaHub.entity.dto;


import com.baomidou.mybatisplus.annotation.TableId;

public class CalendarDTO {
    @TableId("CalendarDTOId")
    public String title;
    public String start;
    public String color;
    //记录exercise，test数量
    public Integer number=0;
    //记录section
    public String section;

    public void setTitle(String section)
    {
        title =section;
    }

    public void setStart(String date)
    {
        start = date;
    }

    public void setColor(){
        if (title.contains("exercise")){
            color = "purple";
        } else if (title.contains("section")) {
            color = "default";
        } else if (title.contains("test")) {
            color = "green";
        }
    }


}
