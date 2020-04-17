package com.by.service;

import com.by.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    int saveComment(Comment comment);
    Comment getCommentByParentId(Long pid);
    List<Comment> getReplyListByParentId(Long id);
}
