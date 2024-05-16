package com.JavaHub.service;

import cn.hutool.core.bean.BeanUtil;
import com.JavaHub.Exception.ServiceException;
import com.JavaHub.common.Constants;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.UserDTO;
import com.JavaHub.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
        return saveOrUpdate(user);
    }

    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userDTO.getUserid());
        queryWrapper.eq("password",userDTO.getPassword());
        User one = getOne(queryWrapper);
        if(one != null){
            BeanUtil.copyProperties(one,userDTO,true);
            return userDTO;
        } else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    public User register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userDTO.getUserid());
        User one = getOne(queryWrapper);
        if(one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one);
        } else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    public User pswd(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userDTO.getUserid());
        User user = getOne(queryWrapper);
        if(user == null){
            throw new ServiceException(Constants.CODE_400,"用户不存在");
        } else{
            return user;
        }
    }

    public boolean reset(UserDTO userDTO) {
        User user = new User();
        user.setPassword("123456");
        user.setUserid(userDTO.getUserid());
        return saveOrUpdate(user);

    }
}
