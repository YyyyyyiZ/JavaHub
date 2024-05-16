package com.JavaHub.controller;


import com.JavaHub.entity.LearnRecord;
import com.JavaHub.entity.Note;
import com.JavaHub.entity.User;
import com.JavaHub.mapper.LearnRecordMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/learnrecord")
@CrossOrigin(origins = "*")
public class LearnRecordController {

    @Resource
    private LearnRecordMapper learnRecordMapper;

    @PostMapping("/insert")
    public void insertLearnRecord(@RequestBody LearnRecord learnRecord){
        String userid = learnRecord.getUserid();
        String secid = learnRecord.getSecid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        LearnRecord lr = learnRecordMapper.selectExist(userid,format,secid);
        if (lr==null){
            Integer timelast=0;
            learnRecordMapper.insertRecord(userid,format,timelast,secid);
        }
    }

    @PostMapping("/update")
    public void updateLearnRecord(@RequestBody LearnRecord learnRecord){
        String userid = learnRecord.getUserid();
        String secid = learnRecord.getSecid();
        double timelast = learnRecord.getTimelast();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        LearnRecord lr = learnRecordMapper.selectExist(userid,format,secid);
        if (lr==null){
            learnRecordMapper.insertRecord(userid,format,timelast,secid);
        } else {
            Integer learnRecordid=lr.getLearnrecordid();
            double oldtime = lr.getTimelast();
            double newtime = oldtime+timelast;
            learnRecordMapper.updateRecord(learnRecordid,newtime);
        }
    }


    @PostMapping("/menu")
    public List<Boolean> menu(@RequestBody User user){
        String userid = user.getUserid();
        List<Boolean> menus = new ArrayList<>();
        List<String> chapids=learnRecordMapper.selectLearned(userid);
        Integer max = 0;
        //对于新用户，解锁第一章
        if (chapids.size()==0){
            max=0;
        }else {
            for(String chapid:chapids){
                Integer num = Integer.parseInt(chapid);
                if (num>max) {
                    max=num;
                }
            }
        }
        for (int i=0;i<max+1;i++){
            menus.add(false);
        }
        for (int j = max+1;j<10;j++){
            menus.add(true);
        }
        return menus;
    }

    @PostMapping("/{userid}")
    public List<Integer> sumchap(@PathVariable String userid){
        List<Integer> complete=new ArrayList<>();
        Integer num1=learnRecordMapper.chaptime(userid,1);
        Integer num2=learnRecordMapper.chaptime(userid,2);
        Integer num3=learnRecordMapper.chaptime(userid,3);
        Integer num4=learnRecordMapper.chaptime(userid,4);
        Integer num5=learnRecordMapper.chaptime(userid,5);
        Integer num6=learnRecordMapper.chaptime(userid,6);
        Integer num7=learnRecordMapper.chaptime(userid,7);
        Integer num8=learnRecordMapper.chaptime(userid,8);
        Integer num9=learnRecordMapper.chaptime(userid,9);
        Integer num10=learnRecordMapper.chaptime(userid,10);
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
        complete.removeAll(Collections.singleton(null));
        if(complete==null||complete.size()==0){
            if(learnRecordMapper.allchaptime(1)==null)
                num1=0;
            else
                num1=(int) Math.round(learnRecordMapper.allchaptime(1));
            if(learnRecordMapper.allchaptime(2)==null)
                num2=0;
            else
                num2=(int) Math.round(learnRecordMapper.allchaptime(2));
            if(learnRecordMapper.allchaptime(3)==null)
                num3=0;
            else
                num3=(int) Math.round(learnRecordMapper.allchaptime(3));
            if(learnRecordMapper.allchaptime(4)==null)
                num4=0;
            else
                num4=(int) Math.round(learnRecordMapper.allchaptime(4));
            if(learnRecordMapper.allchaptime(5)==null)
                num5=0;
            else
                num5=(int) Math.round(learnRecordMapper.allchaptime(5));
            if(learnRecordMapper.allchaptime(6)==null)
                num6=0;
            else
                num6=(int) Math.round(learnRecordMapper.allchaptime(6));
            if(learnRecordMapper.allchaptime(7)==null)
                num7=0;
            else
                num7=(int) Math.round(learnRecordMapper.allchaptime(7));
            if(learnRecordMapper.allchaptime(8)==null)
                num8=0;
            else
                num8=(int) Math.round(learnRecordMapper.allchaptime(8));
            if(learnRecordMapper.allchaptime(9)==null)
                num9=0;
            else
                num9=(int) Math.round(learnRecordMapper.allchaptime(9));
            if(learnRecordMapper.allchaptime(10)==null)
                num10=0;
            else
                num10=(int) Math.round(learnRecordMapper.allchaptime(10));
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
        }
        return complete;
    }

    //    所有人学习时长
//    @PostMapping("/all")
//    public List<Integer> sumchap(){
//        List<Integer> complete=new ArrayList<>();
//        Integer num1,num2,num3,num4,num5,num6,num7,num8,num9,num10;
//        if(learnRecordMapper.allchaptime(1)==null)
//            num1=0;
//        else
//            num1=(int) Math.round(learnRecordMapper.allchaptime(1));
//        if(learnRecordMapper.allchaptime(2)==null)
//            num2=0;
//        else
//            num2=(int) Math.round(learnRecordMapper.allchaptime(2));
//        if(learnRecordMapper.allchaptime(3)==null)
//            num3=0;
//        else
//            num3=(int) Math.round(learnRecordMapper.allchaptime(3));
//        if(learnRecordMapper.allchaptime(4)==null)
//            num4=0;
//        else
//            num4=(int) Math.round(learnRecordMapper.allchaptime(4));
//        if(learnRecordMapper.allchaptime(5)==null)
//            num5=0;
//        else
//            num5=(int) Math.round(learnRecordMapper.allchaptime(5));
//        if(learnRecordMapper.allchaptime(6)==null)
//            num6=0;
//        else
//            num6=(int) Math.round(learnRecordMapper.allchaptime(6));
//        if(learnRecordMapper.allchaptime(7)==null)
//            num7=0;
//        else
//            num7=(int) Math.round(learnRecordMapper.allchaptime(7));
//        if(learnRecordMapper.allchaptime(8)==null)
//            num8=0;
//        else
//            num8=(int) Math.round(learnRecordMapper.allchaptime(8));
//        if(learnRecordMapper.allchaptime(9)==null)
//            num9=0;
//        else
//            num9=(int) Math.round(learnRecordMapper.allchaptime(9));
//        if(learnRecordMapper.allchaptime(10)==null)
//            num10=0;
//        else
//            num10=(int) Math.round(learnRecordMapper.allchaptime(10));
//        complete.add(num1);
//        complete.add(num2);
//        complete.add(num3);
//        complete.add(num4);
//        complete.add(num5);
//        complete.add(num6);
//        complete.add(num7);
//        complete.add(num8);
//        complete.add(num9);
//        complete.add(num10);
//        return complete;
//    }
}
