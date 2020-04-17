package com.by.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    //头像
    private String avatar;
    private Date createTime;
    //评论功能
    private List<Comment> replyComments = new ArrayList<>();
    @ToString.Exclude
    private Comment parentComment;
    private Blog blog;
    private boolean adminComment;
}
