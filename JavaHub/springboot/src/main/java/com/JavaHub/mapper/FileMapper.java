package com.JavaHub.mapper;

import com.JavaHub.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<Image> {

    @Select("SELECT url FROM file WHERE userid=#{userid} AND secid=#{secid}")
    List<Image> selectFile(String userid, String secid);
}
