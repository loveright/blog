package com.by.mapper;

import com.by.po.Tag;
import com.by.po.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TagMapper {
    int saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> listTag();
    List<Tag> listTagByList(List<Long> ids);
    int updateTag(Tag tag);
    void deleteTag(Long id);
    List<Tag> getTagWithBlogsOrderTop();
}
