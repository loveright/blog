package com.by.service;

import com.by.mapper.CommentMapper;
import com.by.po.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommentMapper commentMapper;
    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查出所有的顶级结点，对象的parentComment与 replyComments均未赋值，需要递归赋值
        List<Comment> comments = commentMapper.findByBlogIdParentIdNull(blogId, null);
        return eachComment(comments);
    }
    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();

        if(parentCommentId == null){
            comment.setParentComment(null);
        }else {
//            Comment c = new Comment();
//            c.setId(new Long(-1));
            comment.setParentComment(commentMapper.getCommentByParentId(parentCommentId));
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    @Override
    public Comment getCommentByParentId(Long pid) {
        return null;
    }

    @Override
    public List<Comment> getReplyListByParentId(Long id) {
        return commentMapper.getReplyListByParentId(id);
    }

    /**
     * 循环每个顶级的评论节点，赋值到一个新的集合中
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合，查出根节点子集，并未该结点的父级结点赋值
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
//            List<Comment> replys1 = comment.getReplyComments();
            //根据父级id找出子集
            List<Comment> replys1 = commentMapper.getReplyListByParentId(comment.getId());
            for(Comment reply1 : replys1) {
                //为该对象的父级对象赋值
                reply1.setParentComment(comment);
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (commentMapper.getReplyListByParentId(comment.getId()).size()>0) {
//            List<Comment> replys = comment.getReplyComments();
            List<Comment> replys = commentMapper.getReplyListByParentId(comment.getId());
            for (Comment reply : replys) {
                reply.setParentComment(comment);
                tempReplys.add(reply);
                if (commentMapper.getReplyListByParentId(reply.getId()).size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
