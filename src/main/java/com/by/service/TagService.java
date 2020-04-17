package com.by.service;

import com.by.po.Tag;

import java.util.List;

public interface TagService {
    int saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> listTag();
    List<Tag> listTagByList(String ids);
    int updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    List<Tag> getTagWithBlogsOrderTop(Integer size);
}
