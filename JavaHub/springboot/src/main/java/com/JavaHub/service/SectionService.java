package com.JavaHub.service;

import cn.hutool.core.bean.BeanUtil;
import com.JavaHub.Exception.ServiceException;
import com.JavaHub.common.Constants;
import com.JavaHub.entity.Section;
import com.JavaHub.entity.User;
import com.JavaHub.mapper.SectionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SectionService extends ServiceImpl<SectionMapper, Section>{

    public boolean saveSection(Section section) {
        return saveOrUpdate(section);
    }
}
