package com.JavaHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "learnrecord")
public class LearnRecord {

    @TableId(value = "learnrecord",type= IdType.AUTO)
    private Integer learnrecordid;
    private String userid;
    private String secid;
    private double timelast;
    private String learndate;
    private String note;

}
