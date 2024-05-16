package com.JavaHub.mapper;

import com.JavaHub.entity.Wrongset;
import com.JavaHub.entity.dto.WrongDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WrongsetMapper extends BaseMapper<Wrongset> {

    @Select("select wrongid,wrongset.exerid,exerques,exerans,exertype,knowpoint,remarks " +
            "from exercise,wrongset,knowexer,knowledge " +
            "where wrongset.userid=#{userid} and " +
            "wrongset.exerid=exercise.exerid and " +
            "exercise.exerid=knowexer.exerid and " +
            "knowexer.knowid=knowledge.knowid")
    List<WrongDTO> select(@Param("userid") String userid);

    @Delete("delete from wrongset where wrongid=#{wrongid}")
    boolean deleteByID(@Param("wrongid") Integer wrongid);

    @Update("UPDATE wrongset SET remarks=#{remarks} WHERE wrongid=#{wrongid}")
    boolean updateRemark(Integer wrongid, String remarks);

    @Insert("INSERT INTO  wrongset(userid,exerid) VALUES (#{userid},#{exerid})")
    void newWrong(String userid, int exerid);
}
