package com.JavaHub.mapper;

import com.JavaHub.entity.Exercise;
import com.JavaHub.entity.Test;
import com.JavaHub.entity.dto.ExerrecordDTO;
import com.JavaHub.entity.dto.TestSetDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapper<Test> {

    @Select("SELECT * from test WHERE userid=#{userid} and chapid=#{chapid}")
    List<Test> selectAll(String userid, String chapid);

    @Select("SELECT test.testid,exercise.exerid,exerques,exerans,exertype,knowpoint,rightwrong " +
            "from test,testset,exercise,knowexer,knowledge " +
            "WHERE test.testid=#{testid} " +
            "and test.userid=#{userid} " +
            "and test.testid=testset.testid " +
            "and testset.exerid=exercise.exerid " +
            "and exercise.exerid=knowexer.exerid " +
            "and knowexer.knowid=knowledge.knowid")
    List<TestSetDTO> selectOneTest(String userid,Integer testid);

    @Update("UPDATE testset set collected=#{collected} WHERE testid=#{testid} and exerid=#{exerid}")
    boolean change(Integer testid, Integer exerid, String collected);

    @Select("SELECT userid FROM test WHERE testid=#{testid}")
    String selectUser(Integer testid);


    @Insert("INSERT INTO test(userid,timecost,score,testdate,chapid,remark) " +
            "VALUES(#{userid},#{timecost},#{score},#{testdate},#{chapid},#{remark})")
    boolean updateTest(String userid, int timecost, int score, String testdate, String chapid,String remark);

    @Insert("INSERT INTO testset(exerid,testid,rightwrong,collected) " +
            "VALUES(#{exerid},#{testid},#{rightwrong},#{collected})")
    boolean updateTestSet(int exerid, int testid, String rightwrong, String collected);

    @Select("SELECT testid from test WHERE remark=#{remark}")
    Integer selectRemark(String remark);

    @Update("UPDATE test SET remark=#{newRemark} WHERE testid=#{testid}")
    void updateRemark(Integer testid, String newRemark);

    @Select("SELECT MAX(testid) FROM test")
    Integer selectMax();
}
