package com.by.mapper;

import com.by.po.Blog;
import com.by.vo.BlogAboutSearch;
import com.by.vo.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper {
    Blog getBlog(Long id);
    List<Blog> listBlog();
    List<BlogQuery> listBlogWithType();
    List<BlogQuery> listBlogFromSearch(BlogAboutSearch bt);
    int saveBlog(Blog blog);
    int saveBlogWithTags(Blog blog);
    int updateBlog(Blog blog);
    int updateBlogWithTags(Blog blog);
    void deleteBlog(Long id);
    void deleteBlogWithTags(Long blogId);
    Blog getBlogWithTypeWitnTagsByBlogId(Long bid);
    List<Blog> getBlogWithTypeAndWithUser();
    Blog getBlogWithTagsAndWithUser(Long id);
    //查询推荐的博客
    List<Blog> getBlogIsRecommend(Integer num);
    //根据关键字查询博客
    List<Blog> getBlogBySearchWithQuery(String query);
    @Update("update t_blog set views = views+1 where id = #{id}")
    int updateViews(Long id);
    List<Blog> getBlogsByTypeId(Long tid);
    List<Blog> getBlogsByTagId(Long tid);
    @Select("select date_format(b.update_time,'%Y') as year from t_blog b group by year order by year desc")
    List<String> findGroupYear();
    //@Select("select * from t_blog b where date_format(b.update_time,'%Y')=#{year}")
    List<Blog> findBlogByYear(String year);
    @Select("select count(id) from t_blog where published = true")
    Long countBlog();
}
