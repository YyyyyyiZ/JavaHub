package com.JavaHub.service;

import cn.hutool.core.bean.BeanUtil;
import com.JavaHub.Exception.ServiceException;
import com.JavaHub.common.Constants;
import com.JavaHub.entity.Knowledge;
import com.JavaHub.mapper.KnowledgeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeService extends ServiceImpl<KnowledgeMapper, Knowledge> {
    public boolean saveKnowledge(Knowledge knowledge) {
        return saveOrUpdate(knowledge);
    }
}
