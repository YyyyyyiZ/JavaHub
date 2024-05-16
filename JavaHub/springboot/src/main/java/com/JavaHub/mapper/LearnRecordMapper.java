package com.JavaHub.mapper;

import com.JavaHub.entity.LearnRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LearnRecordMapper {
    @Select("SELECT * FROM learnrecord WHERE userid=#{userid} AND learndate=#{format} AND secid=#{secid}")
    LearnRecord selectExist(String userid, String format, String secid);

    @Insert("INSERT INTO learnrecord(userid,learndate,timelast,secid) VALUES(#{userid},#{format},#{timelast},#{secid})")
    void insertRecord(String userid, String format, double timelast,String secid);

    @Update("UPDATE learnrecord SET timelast=#{newtime} WHERE learnrecordid=#{learnrecordid}")
    void updateRecord(Integer learnrecordid, double newtime);

    @Select("select sum(learnrecord.timelast) from learnrecord,section" +
            " where learnrecord.userid=#{userid}" +
            " and section.secid=javahub.learnrecord.secid" +
            " and section.chapid=#{chapid}")
    Integer chaptime(String userid,Integer chapid);


    @Select("SELECT DISTINCT chapter.chapid from chapter,section,learnrecord " +
            "WHERE userid=#{userid} AND " +
            "learnrecord.secid=section.secid AND " +
            "section.chapid=chapter.chapid")
    List<String> selectLearned(String userid);

    //    所有人的学习时长
    @Select("select sum(learnrecord.timelast) from learnrecord,section" +
            " where section.secid=javahub.learnrecord.secid" +
            " and section.chapid=#{chapid}")
    Double allchaptime(Integer chapid);


}
