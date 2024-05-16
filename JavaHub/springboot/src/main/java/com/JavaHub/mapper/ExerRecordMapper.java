package com.JavaHub.mapper;

import com.JavaHub.entity.ExerRecord;
import com.JavaHub.entity.Exercise;
import com.JavaHub.entity.dto.ExerrecordDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExerRecordMapper extends BaseMapper<ExerRecord> {
    @Insert("INSERT INTO exerrecord(userid,exerid,rightwrong,exerdate,timecost) " +
            "values(#{userid},#{exerid},#{rightwrong},#{exerdate},#{timecost})")
    boolean insertOne(String userid, int exerid, String rightwrong,String exerdate,Integer timecost);

    @Select("SELECT exercise.exerid,rightwrong,exerdate " +
            "from exerrecord,exercise,knowexer,knowledge,section " +
            "WHERE exerrecord.userid=#{userid} " +
            "and exerrecord.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid " +
            "and knowledge.secid=section.secid " +
            "and section.chapid=#{chapid}")
    List<ExerrecordDTO> exerrecord(String userid, String chapid);

    @Select("SELECT testset.exerid,rightwrong,testdate AS exerdate " +
            "FROM test,testset,knowexer,knowledge,section " +
            "WHERE test.userid=#{userid} " +
            "and test.testid=testset.testid " +
            "and testset.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid " +
            "and knowledge.secid=section.secid " +
            "and section.chapid=#{chapid}")
    List<ExerrecordDTO> testrecord(String userid, String chapid);

    @Select("SELECT DISTINCT exerdate " +
            "from exerrecord,exercise,knowexer,knowledge,section " +
            "WHERE exerrecord.userid=#{userid} " +
            "and exerrecord.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid " +
            "and knowledge.secid=section.secid " +
            "and section.chapid=#{chapid}")
    List<String> exerdate(String userid, String chapid);

    @Select("SELECT DISTINCT testdate " +
            "FROM test,testset,knowexer,knowledge,section " +
            "WHERE test.userid=#{userid} " +
            "and test.testid=testset.testid " +
            "and testset.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid " +
            "and knowledge.secid=section.secid " +
            "and section.chapid=#{chapid}")
    List<String> testdate(String userid, String chapid);

    @Select("SELECT DISTINCT knowexer.knowid,knowexer.exerid From knowexer,knowledge,section " +
            "WHERE knowexer.knowid=knowledge.knowid " +
            "AND knowledge.secid=section.secid " +
            "AND section.chapid=#{chapid} ")
    List<ExerrecordDTO> selectMatrix(String chapid);

    @Select("SELECT DISTINCT knowexer.exerid From knowexer,knowledge,section " +
            "WHERE knowexer.knowid=knowledge.knowid " +
            "AND knowledge.secid=section.secid " +
            "AND section.chapid=#{chapid} ")
    List<Integer> selectAllExerids(String chapid);

    @Select("SELECT DISTINCT knowexer.knowid From knowexer,knowledge,section " +
            "WHERE knowexer.knowid=knowledge.knowid " +
            "AND knowledge.secid=section.secid " +
            "AND section.chapid=#{chapid} ")
    List<Integer> selectKnowids(String chapid);

    @Select("SELECT DISTINCT exerrecord.exerid " +
            "from exerrecord,exercise,knowexer,knowledge,section " +
            "WHERE exerrecord.userid=#{userid} " +
            "and exerrecord.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid " +
            "and knowledge.secid=section.secid " +
            "and section.chapid=#{chapid}")
    List<Integer> selectDoneEexrids(String userid, String chapid);

    @Select("SELECT * from exercise WHERE exerid=#{exerid}")
    Exercise selectExercise(Integer exerid);
}
