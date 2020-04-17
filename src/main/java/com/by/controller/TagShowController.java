package com.by.controller;

import com.by.po.Blog;
import com.by.po.Tag;
import com.by.po.Type;
import com.by.service.BlogService;
import com.by.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @GetMapping("/tags/{id}")
    public String types(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable Long id){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            List<Tag> tags = tagService.getTagWithBlogsOrderTop(10000);
            if(id == -1){
                id = tags.get(0).getId();
            }
            model.addAttribute("tags",tags);
            List<Blog> blogs = blogService.getBlogsByTagId(id);
            PageHelper.startPage(pageNum, 100);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("activeTagId", id);

        }catch (Exception e){
            logger.info("TagShowController Exception:"+e.getMessage());
        }
        return "tags";
    }
}
