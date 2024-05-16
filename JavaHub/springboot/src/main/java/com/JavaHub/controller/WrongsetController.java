package com.JavaHub.controller;



import com.JavaHub.common.Result;
import com.JavaHub.entity.User;
import com.JavaHub.entity.Wrongset;
import com.JavaHub.entity.dto.UserDTO;
import com.JavaHub.entity.dto.WrongDTO;
import com.JavaHub.mapper.WrongsetMapper;
import com.JavaHub.service.WrongsetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SplittableRandom;

@RestController
@RequestMapping("/wrongset")
@CrossOrigin(origins = "*")
public class WrongsetController {

    @Resource
    private WrongsetMapper wrongsetMapper;


    @PostMapping("/select")
    @ResponseBody
    public List<WrongDTO> select(@RequestBody UserDTO userDTO){
        List<WrongDTO> dtoList = wrongsetMapper.select(userDTO.getUserid());
        List<WrongDTO> norepeat = new ArrayList<>();
        List<Integer> exerids = new ArrayList<>();
        for (WrongDTO dto:dtoList){
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
            if (!exerids.contains(dto.getExerid())){
                exerids.add(dto.getExerid());
                norepeat.add(dto);
            }
            //判断题、填空题 exerques,exerans
        }
        return norepeat;
    }

    @PostMapping ("/delete")
    public boolean delete(@RequestBody WrongDTO wrongDTO){
        return wrongsetMapper.deleteByID(wrongDTO.getWrongid());
    }

    @PostMapping("/update")
    public boolean reset(@RequestBody WrongDTO wrongDTO){
        Integer wrongid = wrongDTO.getWrongid();
        String remarks = wrongDTO.getRemarks();
        return wrongsetMapper.updateRemark(wrongid,remarks);
    }

}

