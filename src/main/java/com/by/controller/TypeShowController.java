package com.by.controller;

import com.by.po.Blog;
import com.by.po.Type;
import com.by.service.BlogService;
import com.by.service.TypeService;
import com.by.vo.BlogQuery;
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
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/types/{id}")
    public String types(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable Long id){
        try{
            List<Type> types = typeService.getTypeWithBlogsOrderTop(10000);
            if(id == -1){
                id = types.get(0).getId();
            }
            model.addAttribute("types",types);
            List<Blog> blogs = blogService.getBlogsByTypeId(id);
            PageHelper.startPage(pageNum, 100);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("activeTypeId", id);
        }catch (Exception e){
            logger.info("TypeShowController Exception:"+e.getMessage());
        }
        return "types";
    }
}
