package com.JavaHub.controller;

import cn.hutool.core.util.StrUtil;
import com.JavaHub.common.Constants;
import com.JavaHub.common.Result;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.CalendarDTO;
import com.JavaHub.entity.dto.UserDTO;
import com.JavaHub.mapper.CalendarMapper;
import com.JavaHub.mapper.UserMapper;
import com.JavaHub.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private CalendarMapper calendarMapper;

    @PostMapping("/calendar")
    public List<CalendarDTO> record(@RequestBody User user){
        Integer k=0;
        List<CalendarDTO> records = new ArrayList<>();
        String userid = user.getUserid();
        //SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        //learnrecord
        List<String> learnDate = calendarMapper.learnDate(userid);
        for (String date:learnDate){
            CalendarDTO oneRecord = new CalendarDTO();
            List<String> section = calendarMapper.countSection(userid,date);
            String temp="";
            for (String oneSection:section){
                temp+=oneSection;
            }
            oneRecord.setTitle("section:"+temp);
            oneRecord.setStart(date);
            oneRecord.setColor();
            records.add(k,oneRecord);
            k++;
        }
        //exerrecord
        List<String> exerrecord = calendarMapper.exerDate(userid);
        for (String date:exerrecord){
            CalendarDTO oneRecord = new CalendarDTO();
            Integer exerNum = calendarMapper.countExer(userid,date);
            oneRecord.setTitle("exercise:"+exerNum);
            oneRecord.setStart(date);
            oneRecord.setColor();
            records.add(k,oneRecord);
            k++;
        }

        //test
        List<String> testrecord = calendarMapper.testDate(userid);
        for (String date:testrecord){
            CalendarDTO oneRecord = new CalendarDTO();
            Integer testNum = calendarMapper.countTest(userid,date);
            oneRecord.setTitle("test:"+testNum);
            oneRecord.setStart(date);
            oneRecord.setColor();
            records.add(k,oneRecord);
            k++;
        }
        return records;

    }

//    新增
    @PostMapping("/userad")
    public Result savead(@RequestBody User user){
        if (user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

//    更新
    @PutMapping ("/userad")
    public Result update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }

//    数据
    @GetMapping("/userad")
    public List<User> find(){
        return userMapper.all();
    }

//    删除
    @DeleteMapping("/userad/{userid}")
    public Result update(@PathVariable String userid){
        userMapper.deleteByID(userid);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String password = userDTO.getPassword();
        String userid = userDTO.getUserid();
        //Hutool校验字符串是否为空
        if(StrUtil.isBlank(password)||StrUtil.isBlank(userid)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String password = userDTO.getPassword();
        String userid = userDTO.getUserid();
        if(StrUtil.isBlank(password)||StrUtil.isBlank(userid)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));
    }

    @PostMapping("/pswd")
    public Result pswd(@RequestBody UserDTO userDTO){
        String userid = userDTO.getUserid();
        //Hutool校验字符串是由为空
        if(StrUtil.isBlank(userid)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        User user = userService.pswd(userDTO);
        return Result.success(user);
    }

    @PostMapping("/reset")
    public boolean reset(@RequestBody UserDTO userDTO){
        return userService.reset(userDTO);
    }

    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping
    @ResponseBody
    public List<User> findAll(){
        return userService.list();
    }

    @DeleteMapping("/{userid}")
    public boolean delete(@PathVariable String userid){
        return userService.removeById(userid);
    }


    @RequestMapping ("/time/{userid}/{date}")
    public Integer[] sumtime(@PathVariable String userid,
                             @PathVariable String[] date){
        Integer exertime[]=new Integer[7];
        Integer testtime[]=new Integer[7];
        Integer learntime[]=new Integer[7];
        Integer totaltime[]=new Integer[7];
        for(Integer i=0;i<7;i++){
            exertime[i]=userMapper.exertime(userid,date[i]);
            if(exertime[i]!=null){
                exertime[i] = exertime[i]/60;
            } else{
                exertime[i]=0;
            }
            testtime[i]=userMapper.testtime(userid, date[i]);
            if(testtime[i]!=null);
            else{
                testtime[i]=0;
            }
            learntime[i]=userMapper.learntime(userid,date[i]);
            if(learntime[i]!=null);
            else{
                learntime[i]=0;
            }
            totaltime[i]=exertime[i]+testtime[i]+learntime[i];
        }
        return totaltime;
    }

    @RequestMapping ("/time/{date}")
    public Integer[] allsumtime(@PathVariable String[] date){
        Integer exertime[]=new Integer[7];
        Integer testtime[]=new Integer[7];
        Integer learntime[]=new Integer[7];
        Integer totaltime[]=new Integer[7];
        for(Integer i=0;i<7;i++){
            exertime[i]=userMapper.sexertime(date[i]);
            if(exertime[i]!=null){
                exertime[i] = exertime[i]/60;
            } else{
                exertime[i]=0;
            }
            testtime[i]=userMapper.stesttime(date[i]);
            if(testtime[i]!=null);
            else{
                testtime[i]=0;
            }
            learntime[i]=userMapper.slearntime(date[i]);
            if(learntime[i]!=null);
            else{
                learntime[i]=0;
            }
            totaltime[i]=exertime[i]+testtime[i]+learntime[i];
        }
        return totaltime;
    }

}
