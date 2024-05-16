package com.JavaHub.mapper;


import com.JavaHub.entity.Knowledge;
import com.JavaHub.entity.User;
import com.JavaHub.entity.dto.ExerrecordDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    @Select("SELECT knowid,knowpoint,knowdetail from knowledge WHERE secid=#{secid}")
    List<Knowledge> select(String secid);

    @Select("select * from knowledge order by knowid+0")
    List<Knowledge> all();

    @Delete("delete from knowledge where knowid=#{knowid}")
    Integer deleteByID(@Param("knowid") String knowid);


}
