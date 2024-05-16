package com.JavaHub.service;

import cn.hutool.core.bean.BeanUtil;
import com.JavaHub.Exception.ServiceException;
import com.JavaHub.common.Constants;
import com.JavaHub.entity.Exercise;
import com.JavaHub.entity.User;
import com.JavaHub.mapper.ExerciseMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService extends ServiceImpl<ExerciseMapper, Exercise>{
    public boolean saveExercise(Exercise exercise) {
        return saveOrUpdate(exercise);
    }
}
