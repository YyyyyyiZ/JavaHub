package com.JavaHub.controller;


import com.JavaHub.entity.ExerRecord;
import com.JavaHub.mapper.ExerRecordMapper;
import com.JavaHub.mapper.ExerciseMapper;
import com.JavaHub.mapper.WrongsetMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/exerrecord")
@CrossOrigin(origins = "*")
public class ExerRecordController {

    @Resource
    private WrongsetMapper wrongsetMapper;
    @Resource
    private ExerRecordMapper exerRecordMapper;

    @Resource
    private ExerciseMapper exerciseMapper;

    @PostMapping("/insert")
    public boolean insert(@RequestBody ExerRecord exerRecord){
        String userid = exerRecord.getUserid();
        Integer timecost = exerRecord.getTimecost();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String exerdate = simpleDateFormat.format(date);
        int exerid = exerRecord.getExerid();
        String rightwrong = exerRecord.getRightwrong();
        if (rightwrong.equals("Y")){
            Integer dotimes = exerciseMapper.selectDotimes(exerid)+1;
            Integer righttimes = exerciseMapper.selectRightTimes(exerid)+1;
            exerciseMapper.updateAll(exerid,dotimes,righttimes);
        } else if (rightwrong.equals("N")) {
            Integer dotimes = exerciseMapper.selectDotimes(exerid)+1;
            Integer righttimes = exerciseMapper.selectRightTimes(exerid);
            wrongsetMapper.newWrong(userid,exerid);
            exerciseMapper.updateAll(exerid,dotimes,righttimes);
        }
        return exerRecordMapper.insertOne(userid,exerid,rightwrong,exerdate,timecost);
    }
}
