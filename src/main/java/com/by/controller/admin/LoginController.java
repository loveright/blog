package com.by.controller.admin;

import com.by.po.User;
import com.by.service.BlogService;
import com.by.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        //用于redirect时向前传值
                        RedirectAttributes attributes){
           User user =  userService.checkUser(username,password);
       if(user != null){
           user.setPassword(null);
           session.setAttribute("user",user);
           return "admin/index";
       }else {
           attributes.addFlashAttribute("messsage","用户名或密码错误！");
           return "redirect:/admin";
       }
    }

    @GetMapping("/loginout")
    public String loginout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        try{
            model.addAttribute("newblogs",blogService.getBlogIsRecommend(3));
        }catch (Exception e){
            logger.info("IndexController--newBlogs:"+e.getMessage());
        }
        return "admin/_fragments :: newblogList";
    }
}
