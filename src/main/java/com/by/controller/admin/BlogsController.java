package com.by.controller.admin;

import com.by.mapper.BlogMapper;
import com.by.po.Blog;
import com.by.po.User;
import com.by.service.BlogService;
import com.by.service.TagService;
import com.by.service.TypeService;
import com.by.vo.BlogAboutSearch;
import com.by.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //进入博客界面
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum, 3);
        List<BlogQuery> blogs = blogService.listBlogWithType();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        //为页面搜索框中的类型赋值
        model.addAttribute("types",typeService.listType());
        return "admin/blogs";
    }
    //条件查询博客
    @PostMapping("/blogs/search")
    public String search(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum
    , BlogAboutSearch bt){
        System.out.println(bt+":"+bt.toString());
        System.out.println(bt+":"+bt.getRecommend());
        System.out.println(bt+":"+bt.getR());
        PageHelper.startPage(pageNum, 3);
        List<BlogQuery> blogs = blogService.listBlogFromSearch(bt);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }

    //新增博客
    @GetMapping("blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndBlog(model);
        return "admin/blogs-input";
    }

    private void setTypeAndBlog(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    //修改博客
    @GetMapping("blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlogWithTypeWitnTagsByBlogId(id);
        System.out.println("blog-input-0:"+blog.toString());
        logger.info("blog-input-1:"+blog);
        blog.init();
        model.addAttribute("blog",blog);
        logger.info("blog-input-2:"+blog);
        setTypeAndBlog(model);
        return "admin/blogs-input";
    }
    //修改与新增博客
    @PostMapping("/blogs")
    public String postBlog(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTagByList(blog.getTagIds()));
        int b = blogService.saveBlog(blog);
        if(b == 0){
            logger.info("失败："+blog);
            attributes.addFlashAttribute("message","操作博客失败！");
        }else{
            logger.info("成功："+blog);
            attributes.addFlashAttribute("message","操作博客成功！");
        }
        return "redirect:/admin/blogs";
    }
    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlogAndTags(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/blogs";
    }
}
