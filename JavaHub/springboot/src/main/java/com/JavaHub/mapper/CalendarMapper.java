package com.JavaHub.mapper;

import com.JavaHub.entity.dto.CalendarDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface CalendarMapper extends BaseMapper<CalendarDTO> {
    @Select("SELECT DISTINCT(learndate) FROM learnrecord WHERE userid=#{userid}")
    List<String> learnDate(String userid);

    @Select("SELECT DISTINCT(exerdate) FROM exerrecord WHERE userid=#{userid}")
    List<String> exerDate(String userid);

    @Select("SELECT DISTINCT(testdate) FROM test WHERE userid=#{userid}")
    List<String> testDate(String userid);

    @Select("SELECT secid FROM learnrecord WHERE userid=#{userid} AND learndate=#{date} ")
    List<String> countSection(String userid,String date);

    @Select("SELECT COUNT(*) FROM exerrecord WHERE userid=#{userid} AND exerdate=#{date}")
    Integer countExer(String userid, String date);

    @Select("SELECT COUNT(*) FROM test WHERE userid=#{userid} AND testdate=#{date}")
    Integer countTest(String userid, String date);
}
