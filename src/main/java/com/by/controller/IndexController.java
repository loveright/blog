package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.po.Blog;
import com.by.po.Tag;
import com.by.po.Type;
import com.by.service.BlogService;
import com.by.service.TagService;
import com.by.service.TypeService;
import com.by.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        try{
            PageHelper.startPage(pageNum, 3);
            List<Blog> blogs = blogService.getBlogWithTypeAndWithUser();
            List<Type> types = typeService.getTypeWithBlogsOrderTop(6);
            List<Tag> tags = tagService.getTagWithBlogsOrderTop(10);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("types",types);
            model.addAttribute("tags",tags);
            model.addAttribute("recommendBlogs",blogService.getBlogIsRecommend(8));
        }catch (Exception e){
            logger.info("IndexController--index:"+e.getMessage());
        }
        return "index";
    }
    @PostMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @RequestParam String query){
        try{
            PageHelper.startPage(pageNum, 10);
            List<Blog> blogs = blogService.getBlogBySearchWithQuery(query);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("query",query);
        }catch (Exception e){
            logger.info("IndexController--search:"+e.getMessage());
        }
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        try{
            model.addAttribute("blog",blogService.getBlogWithTagsAndWithUser(id));
        }catch (Exception e){
            logger.info("IndexController--blog:"+e.getMessage());
        }
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        try{
            model.addAttribute("newblogs",blogService.getBlogIsRecommend(3));
        }catch (Exception e){
            logger.info("IndexController--newBlogs:"+e.getMessage());
        }
        return "_fragments :: newblogList";
    }
}
