package com.JavaHub.mapper;

import com.JavaHub.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {

    @Update("UPDATE note SET remark=#{note} WHERE userid=#{userid} and secid=#{secid}")
    void updateNote(String userid, String secid, String note);

    @Select("SELECT remark From note WHERE userid=#{userid} and secid=#{secid}")
    String readNote(String userid, String secid);

}
