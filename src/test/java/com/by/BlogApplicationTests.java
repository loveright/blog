package com.by;

import com.by.po.Blog;
import com.by.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    private BlogService blogService;
    @Test
    void contextLoads() {
        Blog blog = blogService.getBlogWithTypeWitnTagsByBlogId(Long.parseLong("2"));
        System.out.println(blog.toString());
    }

}
