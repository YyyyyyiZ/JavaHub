package com.JavaHub.controller;

import com.JavaHub.entity.Collecset;
import com.JavaHub.entity.Test;
import com.JavaHub.entity.dto.*;
import com.JavaHub.mapper.CollectsetMapper;
import com.JavaHub.mapper.TestMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/collecset")
@CrossOrigin(origins = "*")
public class CollecsetController {

    @Autowired
    private CollectsetMapper collectSetMapper;

    @Resource
    private TestMapper testMapper;


    @PostMapping("/select")
    @ResponseBody
    public List<CollectDTO> select(@RequestBody UserDTO userDTO){

        List<CollectDTO> dtoList = collectSetMapper.select(userDTO.getUserid());
        for (CollectDTO dto:dtoList){
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
            //判断题、填空题 exerques,exerans
        }
        return dtoList;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody CollectDTO collectDTO){
        return collectSetMapper.deleteByID(collectDTO.getCollecid());
    }

    @PostMapping("/update")
    public boolean reset(@RequestBody CollectDTO collectDTO){
        Integer collecid = collectDTO.getCollecid();
        String remarks = collectDTO.getRemarks();
        return collectSetMapper.updateRemark(collecid,remarks);
    }

    @PostMapping("/change")
    public boolean change(@RequestBody TestSetDTO testSetDTO){
        Integer testid=testSetDTO.getTestid();
        Integer exerid = testSetDTO.getExerid();
        String collected=testSetDTO.getCollected();
        //根据testid在test中查找userid
        String userid=testMapper.selectUser(testid);
        if (collected.equals("N")){//取消收藏
            collectSetMapper.cancel(userid,exerid);
        }else if(collected.equals("Y")){
            List<Collecset> collecset1 = collectSetMapper.selectExist(userid,exerid);
            System.out.println(collecset1.size());
            if (collecset1.size()==0){
                collectSetMapper.add(userid,exerid);
            }
        }
        return testMapper.change(testid,exerid,collected);
    }

    @PostMapping("/collect")
    public boolean insert(@RequestBody Collecset collecset){
        String userid = collecset.getUserid();
        int exerid = collecset.getExerid();
        List<Collecset> collecset1 = collectSetMapper.selectExist(userid,exerid);
        System.out.println(collecset1.size());
        if (collecset1.size()==0){
            return collectSetMapper.add(userid,exerid);
        } else {
            return false;
        }
    }
}
