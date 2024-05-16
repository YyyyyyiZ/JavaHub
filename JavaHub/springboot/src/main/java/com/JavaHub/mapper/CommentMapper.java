package com.JavaHub.mapper;

import com.JavaHub.entity.Comment;
import com.JavaHub.entity.dto.CommentDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {


    List<CommentDTO> selectComment(int[] idList);

    Integer newComment(Comment comment);

    @Select("SELECT commentid from exercise WHERE exerid=#{exerid}")
    String selectids(String exerid);

    @Update("UPDATE exercise SET commentid=#{newids} WHERE exerid=#{exerid}")
    boolean updateids(String exerid, String newids);

    @Select("SELECT commentid FROM comment WHERE remark=#{remark}")
    Integer selectInserted(String remark);

    @Update("UPDATE comment set remark=#{newremark} WHERE commentid=#{commentid}")
    void updateCommentRemark(Integer commentid, String newremark);
}
