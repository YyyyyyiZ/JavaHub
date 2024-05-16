package com.JavaHub.mapper;

import com.JavaHub.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> findAll();

//    管理员登录
    @Select("select * from user limit 1")
    User findfir();

    @Insert("INSERT INTO user(userid,username,password,gender,school,grade,verifyques,verifyans) VALUES(#{userid},#{username},#{password},#{gender},#{school},#{grade},#{verifyques},#{verifyans})")
    int insert(User user);


    int update(User user);

    @Delete("delete from user where userid=#{userid}")
    Integer deleteByID(@Param("userid") String userid);


    @Select("select count(*) from user")
    Integer selectTotal();

    @Select("select userid,username,gender,school,grade from user")
    List<User> all();

    @Select("select (select sum(learnrecord.timelast) " +
            " from learnrecord " +
            " where learnrecord.userid=#{userid} and learnrecord.learndate=#{date})+ " +
            " (select sum(test.timecost) " +
            " from test " +
            " where test.userid=#{userid} and test.testdate=#{date})+ " +
            " (select sum(exerrecord.timecost) " +
            " from exerrecord " +
            " where exerrecord.userid=#{userid} and exerrecord.exerdate=#{date})")
    Integer totaltime(String userid, String date);

    @Select("select sum(timecost) from exerrecord" +
            " where userid=#{userid} and exerdate=#{exerdate}")
    Integer exertime(String userid, String exerdate);

    @Select("select sum(timecost) from test" +
            " where userid=#{userid} and testdate=#{testdate}")
    Integer testtime(String userid, String testdate);

    @Select("select sum(timelast) from learnrecord" +
            " where learndate=#{learndate}")
    Integer slearntime(String learndate);

    @Select("select sum(timecost) from exerrecord" +
            " where exerdate=#{exerdate}")
    Integer sexertime(String exerdate);

    @Select("select sum(timecost) from test" +
            " where testdate=#{testdate}")
    Integer stesttime(String testdate);

    @Select("select sum(timelast) from learnrecord" +
            " where userid=#{userid} and learndate=#{learndate}")
    Integer learntime(String userid, String learndate);

    //    正确数
    @Select("select count(*) from exerrecord " +
            "where userid=#{userid} and exerdate=#{exerdate} and rightwrong='Y' ")
    Integer counttrue(String userid,String exerdate);

    //    做题数
    @Select("select count(*) from exerrecord " +
            "where userid=#{userid} and exerdate=#{exerdate} ")
    Integer countexer(String userid,String exerdate);

    //    薄弱知识
    @Select("select knowid " +
            "from javahub.knowexer,javahub.exerrecord " +
            "where javahub.exerrecord.userid=#{userid} and " +
            "javahub.exerrecord.rightwrong='N' and " +
            "javahub.exerrecord.exerid=javahub.knowexer.exerid " +
            "group by knowid " +
            "order by count(javahub.knowexer.exerid) desc " +
            "LIMIT 5")
    List<Integer> lowknow(String userid);

    //    薄弱知识数
    @Select("select count(javahub.knowexer.exerid) " +
            "from javahub.knowexer,javahub.exerrecord " +
            "where javahub.exerrecord.userid=#{userid} and " +
            "javahub.exerrecord.rightwrong='N' and " +
            "javahub.exerrecord.exerid=javahub.knowexer.exerid " +
            "group by knowid " +
            "order by count(javahub.knowexer.exerid) desc " +
            "LIMIT 5")
    List<Integer> lowpoint(String userid);

    //    薄弱章节
    @Select("select secid " +
            "from knowexer,exerrecord,knowledge " +
            "where exerrecord.userid=#{userid} and exerrecord.rightwrong='N' and " +
            "exerrecord.exerid=knowexer.exerid and " +
            "knowledge.knowid=knowexer.knowid " +
            "group by  secid " +
            "order by count(knowexer.exerid) desc " +
            "LIMIT 5")
    List<String> lowsec(String userid);

    //    薄弱章节知识数
    @Select("select count(javahub.knowexer.exerid) " +
            "from javahub.knowexer,javahub.exerrecord,javahub.knowledge " +
            "where javahub.exerrecord.userid=#{userid} and javahub.exerrecord.rightwrong='N' and " +
            "javahub.exerrecord.exerid=javahub.knowexer.exerid and " +
            "javahub.knowledge.knowid=javahub.knowexer.knowid " +
            "group by  secid " +
            "order by count(javahub.knowexer.exerid) desc " +
            "LIMIT 5")
    List<Integer> lowsecpoint(String userid);

//    所有人做题数
    @Select("select count(*) from exerrecord " +
            "where exerdate=#{exerdate} ")
    Integer countex(String exerdate);

//    所有人正确数
    @Select("select count(*) from exerrecord " +
            "where exerdate=#{exerdate} and rightwrong='Y' ")
    Integer counttr(String exerdate);

}

