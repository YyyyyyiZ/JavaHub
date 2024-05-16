package com.JavaHub.mapper;

import com.JavaHub.entity.Exercise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExerciseMapper extends BaseMapper<Exercise> {
    @Select("SELECT count(distinct exercise.exerid) from exercise,knowexer,knowledge " +
            "WHERE secid=#{secid} " +
            "and knowledge.knowid=knowexer.knowid " +
            "and knowexer.exerid=exercise.exerid " +
            "and exertype=#{type}")
    Integer selectAll(String secid, String type);

    @Select("SELECT count(distinct exercise.exerid) from exerrecord,exercise,knowexer,knowledge " +
            "WHERE userid=#{userid} and exertype=#{type} " +
            "and exerrecord.exerid=exercise.exerid " +
            "and knowledge.knowid=knowexer.knowid " +
            "and knowexer.exerid=exercise.exerid " +
            "and secid=#{secid}")
    Integer selectDone(String userid, String type,String secid);

    @Select("SELECT exercise.exerid,exerques,exerans,commentid,exertype,knowpoint " +
            "from exercise,knowexer,knowledge " +
            "WHERE secid=#{secid} and knowledge.knowid=knowexer.knowid " +
            "and knowexer.exerid=exercise.exerid " +
            "and exertype=#{exertype}")
    List<Exercise> selectType(String secid, String exertype);

    @Select("SELECT * from exercise order by exerid*1")
    List<Exercise> all();

    @Delete("delete from exercise where exerid=#{exerid}")
    Integer deleteByID(@Param("exerid") String exerid);

    @Update("update exercise set exerques=#{exerques},exertype=#{exertype},exerans=#{exerans} where exerid=#{exerid}")
    boolean updateexer(Integer exerid,String exerques,String exertype,String exerans);

    @Insert("INSERT INTO exercise(exerid,exertype,exerques,exerans,dotimes,righttimes,level,commentid) " +
            "VALUES (#{exerid},#{exertype},#{exerques},#{exerans},0,0,1,null)")
    boolean insertex(Integer exerid,String exertype,String exerques,String exerans);

    @Insert("INSERT INTO knowexer(knowid,exerid) VALUES(#{knowid},#{exerid})")
    boolean insertknex(Integer knowid,Integer exerid);

    @Select("Select count(distinct knowexer.exerid) from knowexer,knowledge,section " +
            "where section.chapid=#{chapid} " +
            "and section.secid=knowledge.secid " +
            "and knowledge.knowid=knowexer.knowid")
    Integer selectChap(Integer chapid);

    @Select("SELECT dotimes from exercise WHERE exerid=#{exerid}")
    Integer selectDotimes(int exerid);

    @Select("SELECT righttimes from exercise WHERE exerid=#{exerid}")
    Integer selectRightTimes(int exerid);

    @Update("update exercise SET dotimes=#{dotimes} AND righttimes=#{righttimes} WHERE exerid=#{exerid}")
    void updateAll(int exerid, Integer dotimes, Integer righttimes);
}
