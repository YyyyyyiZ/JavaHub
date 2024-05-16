package com.JavaHub.mapper;

import com.JavaHub.entity.Collecset;
import com.JavaHub.entity.dto.CollectDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectsetMapper extends BaseMapper<Collecset> {

    @Select("select collecid,exerques,exerans,knowpoint,exertype,remarks " +
            "from exercise,collecset,knowexer,knowledge " +
            "where collecset.userid=#{userid} and " +
            "collecset.exerid=exercise.exerid and " +
            "exercise.exerid=knowexer.exerid and " +
            "knowexer.knowid=knowledge.knowid")
    List<CollectDTO> select(@Param("userid") String userid);

    @Delete("delete from collecset where collecid=#{collecid}")
    boolean deleteByID(@Param("collecid") Integer collecid);

    @Update("UPDATE collecset SET remarks=#{remarks} WHERE collecid=#{collecid}")
    boolean updateRemark(Integer collecid, String remarks);

    @Delete("DELETE from collecset WHERE exerid=#{exerid} and userid=#{userid}")
    boolean cancel(String userid, Integer exerid);

    @Insert("INSERT INTO collecset(userid,exerid) values(#{userid},#{exerid})")
    boolean add(String userid, Integer exerid);

    @Select("SELECT * from collecset WHERE userid=#{userid} AND exerid=#{exerid}")
    List<Collecset> selectExist(String userid, int exerid);
}
