package com.JavaHub.controller;

import com.JavaHub.entity.*;
import com.JavaHub.entity.dto.TestDTO;
import com.JavaHub.entity.dto.TestSetDTO;
import com.JavaHub.mapper.CollectsetMapper;
import com.JavaHub.mapper.ExerRecordMapper;
import com.JavaHub.mapper.TestMapper;
import com.JavaHub.mapper.WrongsetMapper;
import com.JavaHub.service.DKTService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Resource
    private TestMapper testMapper;

    @Resource
    private CollectsetMapper collectsetMapper;

    @Resource
    private WrongsetMapper wrongsetMapper;

    @Resource
    private DKTService dktService;


    @PostMapping("/selectAll")
    @ResponseBody
    public List<Test> selectALL(@RequestBody TestDTO testDTO){

        String userid = testDTO.getUserid();
        String chapid = testDTO.getChapid();
        return testMapper.selectAll(userid,chapid);

    }

    @PostMapping("/selectOne")
    @ResponseBody
    public List<TestSetDTO> selectOnetest(@RequestBody TestDTO testDTO){

        String userid=testDTO.getUserid();
        Integer testid=testDTO.getTestid();
        List<TestSetDTO> dtoList = testMapper.selectOneTest(userid,testid);
        List<Integer> exerids = new ArrayList<>();
        List<TestSetDTO> finallist = new ArrayList<>();
        for (TestSetDTO dto:dtoList){
            //exerques,choice,answer
            if (dto.getExertype().equals("多选题")){
                dto.split_ques();
                dto.split_ans();
            }
            //exerques,choice,exerans
            if (dto.getExertype().equals("单选题")){
                dto.split_ques();
            }
            //判断题、填空题 exerques,exerans
            if (!(exerids.contains(dto.getExerid()))){
                finallist.add(dto);
                exerids.add(dto.getExerid());
            }
            List<Collecset> collected = collectsetMapper.selectExist(userid,dto.getExerid());
            if (collected.size()==0){
                dto.setCollected("N");
            } else {
                dto.setCollected("Y");
            }
        }
        return finallist;
    }

    @PostMapping("/newTest")
    public List<List> newTest(@RequestBody Test test){
        String userid = test.getUserid();
        String chapid = test.getChapid();
        List<Exercise> lfinal = dktService.newTest(userid,chapid);
        List<Exercise> singleCh=new ArrayList<>();
        List<Exercise> multiCh=new ArrayList<>();
        List<Exercise> fill=new ArrayList<>();
        List<Exercise> judge=new ArrayList<>();
        List<Integer> singleid=new ArrayList<>();
        List<Integer> multiid = new ArrayList<>();
        List<Integer> fillid = new ArrayList<>();
        List<Integer> judgeid = new ArrayList<>();
        for (Exercise dto:lfinal){
            //exerques,choice,answer
            if (dto.getExertype().equals("多选题")){
                dto.split_ques();
                dto.split_ans();
            }
            //exerques,choice,exerans
            if (dto.getExertype().equals("单选题")){
                dto.split_ques();
                System.out.println(dto);
            }
            //else 判断题、填空题 exerques,exerans
            if(dto.getExertype().equals("单选题") & !(singleid.contains(dto.getExerid()))){
                singleCh.add(dto);
                singleid.add(dto.getExerid());
            }
            else if (dto.getExertype().equals("多选题") & !(multiid.contains(dto.getExerid()))) {
                multiCh.add(dto);
                multiid.add(dto.getExerid());
            } else if (dto.getExertype().equals("填空题") & !(fillid.contains(dto.getExerid()))) {
                fill.add(dto);
                fillid.add(dto.getExerid());
            } else if (dto.getExertype().equals("判断题") & !(judgeid.contains(dto.getExerid()))) {
                judge.add(dto);
                judgeid.add(dto.getExerid());
            }
        }
        List<List> list4= new ArrayList<>();
        list4.add(singleCh);
        list4.add(multiCh);
        list4.add(fill);
        list4.add(judge);
        return list4;
    }

    @PostMapping("/updateTest")
    public boolean submit(@RequestBody Test test){
        String userid= test.getUserid();
        int timecost = test.getTimecost();
        int score = test.getScore();
        String chapid = test.getChapid();
        String remark = "a"+userid;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String testdate = simpleDateFormat.format(date);
        return testMapper.updateTest(userid,timecost,score,testdate,chapid,remark);
    }


    @PostMapping("/getid")
    public Integer getid(@RequestBody User user){
        String userid=user.getUserid();
        String remark = "a"+userid;
        Integer testid = testMapper.selectRemark(remark);
        testid=testMapper.selectMax();
        String newRemark="b"+userid;
        testMapper.updateRemark(testid+1,newRemark);
        return  testid;
    }

    @PostMapping("/updateTestSet")
    public boolean updateTestSet(@RequestBody TestSet testSet){
        int exerid=testSet.getExerid();
        int testid= testSet.getTestid();
        String rightwrong= testSet.getRightwrong();
        String userid= testSet.getUserid();
        String collected;
        List<Collecset> collecset=collectsetMapper.selectExist(userid,exerid);
        if (collecset.size()==0){
            collected="N";
        }else{
            collected="Y";
        }
        //做错，更新该用户的wrongset
        if (rightwrong.equals("N")){
            wrongsetMapper.newWrong(userid,exerid);
        }
        return testMapper.updateTestSet(exerid,testid,rightwrong,collected);
    }

}
