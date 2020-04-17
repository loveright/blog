package com.by.controller;

import com.by.po.Blog;
import com.by.po.Comment;
import com.by.po.User;
import com.by.service.BlogService;
import com.by.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        try{
            model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        }catch (Exception e){
            logger.info("CommentController Exception--comments:"+e.getMessage());
        }
        return "blog::commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = null;
        try{
            blogId = comment.getBlog().getId();
            Long pid = comment.getParentComment().getId();
            comment.setBlog(blogService.getBlogWithTagsAndWithUser(blogId));
            User user = (User) session.getAttribute("user");
            if(user != null){
                comment.setAvatar(user.getAvatar());
                comment.setAdminComment(true);
            }else{
                Random r = new Random();
                int i = r.nextInt(21)+1;
                comment.setAvatar(avatar+i+".jpg");
            }
            commentService.saveComment(comment);
        }catch (Exception e){
            logger.info("CommentController Exception--post:"+e.getMessage());
        }

        return "redirect:/comments/" + blogId;
    }
}
