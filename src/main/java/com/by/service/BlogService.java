package com.by.service;

import com.by.po.Blog;
import com.by.vo.BlogAboutSearch;
import com.by.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    List<Blog> listBlog();
    List<BlogQuery> listBlogWithType();
    List<BlogQuery> listBlogFromSearch(BlogAboutSearch bt);
    int saveBlog(Blog blog);
    int updateBlog(Long id,Blog blog);
    int updateBlogWithTags(Blog blog);
    void deleteBlog(Long id);
    //删除中间表
    void deleteBlogWithTags(Long blogId);
    Blog getBlogWithTypeWitnTagsByBlogId(Long bid);
    //级联删除博客及其中间表
    void deleteBlogAndTags(Long bid);
    //前端页面展示
    List<Blog> getBlogWithTypeAndWithUser();
    Blog getBlogWithTagsAndWithUser(Long id);
    List<Blog> getBlogIsRecommend(Integer num);
    List<Blog> getBlogBySearchWithQuery(String query);
    Blog getBlogContentConvertHtml(Long id);
    List<Blog> getBlogsByTypeId(Long tid);
    List<Blog> getBlogsByTagId(Long tid);
    Map<String,List<Blog>> archivesBlog();
    Long countBlog();
}
