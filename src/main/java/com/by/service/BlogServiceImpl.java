package com.by.service;

import com.by.exception.NotFoundException;
import com.by.mapper.BlogMapper;
import com.by.po.Blog;
import com.by.po.User;
import com.by.utils.MarkdownUtils;
import com.by.utils.MyBeanUtils;
import com.by.vo.BlogAboutSearch;
import com.by.vo.BlogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    HttpSession session;
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }
    @Transactional
    @Override
    public List<Blog> listBlog() {
        List<Blog> blogs = blogMapper.listBlog();
        return blogs;
    }
    @Transactional
    @Override
    public List<BlogQuery> listBlogWithType() {
        List<BlogQuery> blogWithType = blogMapper.listBlogWithType();
        return blogWithType;
    }
    @Transactional
    @Override
    public List<BlogQuery> listBlogFromSearch(BlogAboutSearch bt) {
        return blogMapper.listBlogFromSearch(bt);
    }
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    @Override
    public int saveBlog(Blog blog) {
        //blog的id不是空则为修改，为空则为新增
        if (blog.getId() == null) {
            blog.setUser((User) session.getAttribute("user"));
            blog.setUpdateTime(new Date());
            blog.setCreateTime(new Date());
            blog.setViews(0);
            blogMapper.saveBlog(blog);
            return blogMapper.saveBlogWithTags(blog);
        }else{
            Blog b = blogMapper.getBlog(blog.getId());
            logger.info("blog:"+b);
            //BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
            blog.setCreateTime(b.getCreateTime());
            blog.setViews(b.getViews());
            blog.setUpdateTime(new Date());
            blogMapper.updateBlogWithTags(blog);
            blogMapper.deleteBlogWithTags(blog.getId());
            return blogMapper.saveBlogWithTags(blog);
        }
    }
    @Transactional
    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog b = blogMapper.getBlog(id);
        if(b == null){
            throw new NotFoundException("该博客不存在！");
        }
        BeanUtils.copyProperties(blog,b);
        return blogMapper.updateBlog(b);
    }
    @Transactional
    @Override
    public int updateBlogWithTags(Blog blog) {
        return blogMapper.updateBlogWithTags(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }
    @Transactional
    @Override
    public void deleteBlogWithTags(Long blogId) {
        blogMapper.deleteBlogWithTags(blogId);
    }
    @Transactional
    @Override
    public Blog getBlogWithTypeWitnTagsByBlogId(Long bid) {
        return blogMapper.getBlogWithTypeWitnTagsByBlogId(bid);
    }
    @Transactional
    @Override
    public void deleteBlogAndTags(Long bid) {
        this.deleteBlogWithTags(bid);
        blogMapper.deleteBlog(bid);
    }
    @Transactional
    @Override
    public List<Blog> getBlogWithTypeAndWithUser() {
        return blogMapper.getBlogWithTypeAndWithUser();
    }
    @Transactional
    @Override
    public Blog getBlogWithTagsAndWithUser(Long id) {
        blogMapper.updateViews(id);
        Blog blog = blogMapper.getBlogWithTagsAndWithUser(id);
        return blog;
    }

    @Transactional
    @Override
    public List<Blog> getBlogIsRecommend(Integer num) {
        return blogMapper.getBlogIsRecommend(num);
    }
    @Transactional
    @Override
    public List<Blog> getBlogBySearchWithQuery(String query) {
        return blogMapper.getBlogBySearchWithQuery(query);
    }

    @Override
    public Blog getBlogContentConvertHtml(Long id) {
        Blog blog = blogMapper.getBlogWithTagsAndWithUser(id);
        if(blog == null)
            throw new NotFoundException("该博客不存在!");
//        Blog b = new Blog();
//        BeanUtils.copyProperties(blog,b);

//        String content = blog.getContent();
//        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog;
    }
    @Transactional
    @Override
    public List<Blog> getBlogsByTypeId(Long tid) {
        return blogMapper.getBlogsByTypeId(tid);
    }
    @Transactional
    @Override
    public List<Blog> getBlogsByTagId(Long tid) {
        return blogMapper.getBlogsByTagId(tid);
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for(String year : years){
            map.put(year,blogMapper.findBlogByYear(year));
        }
        return map;
    }
    @Transactional
    @Override
    public Long countBlog() {
        return blogMapper.countBlog();
    }
}
