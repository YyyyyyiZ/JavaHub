package com.JavaHub.service;
import com.JavaHub.entity.User;
import com.JavaHub.entity.Wrongset;
import com.JavaHub.entity.dto.WrongDTO;
import com.JavaHub.mapper.WrongsetMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WrongsetService extends ServiceImpl<WrongsetMapper,Wrongset>{


    @Resource
    private  WrongsetMapper wrongsetMapper;
    public List<Wrongset> select(String userid) {
        QueryWrapper<Wrongset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        List<Wrongset> wrongsets = wrongsetMapper.selectList(queryWrapper);
        return wrongsets;
    }

}
