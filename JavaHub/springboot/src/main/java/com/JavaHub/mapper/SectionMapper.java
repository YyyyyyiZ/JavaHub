package com.JavaHub.mapper;

import com.JavaHub.entity.Section;
import com.JavaHub.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SectionMapper extends BaseMapper<Section> {
    @Select("select * from section")
    List<Section> findAll();

    @Delete("delete from section where secid=#{secid}")
    Integer deleteByID(@Param("secid") String secid);
}
