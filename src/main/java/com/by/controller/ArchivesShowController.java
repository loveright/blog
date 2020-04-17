package com.by.controller;

import com.by.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/archives")
    public String archives(Model model){
        try{
            model.addAttribute("archivesMap",blogService.archivesBlog());
            model.addAttribute("blogCount",blogService.countBlog());
        }catch (Exception e){
            logger.info("TypeShowController Exception:"+e.getMessage());
        }
        return "archives";
    }
}
