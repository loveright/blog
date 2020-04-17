package com.by.mapper;

import com.by.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper {
    List<Comment> listCommentByBlogId(Long blogId);
    int saveComment(Comment comment);
    Comment getCommentByParentId(Long pid);
    List<Comment> findByBlogIdParentIdNull(@Param("blogId") Long blogId, @Param("blogParentId") Long blogParentId);
    List<Comment> getReplyListByParentId(Long id);
}
