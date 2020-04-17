package com.by.service;

import com.by.exception.NotFoundException;
import com.by.mapper.TagMapper;
import com.by.po.Tag;
import com.by.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        int n = tagMapper.saveTag(tag);
        return n;
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
    }
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        Tag tag = tagMapper.getTagByName(name);
        return tag;
    }
    @Transactional
    @Override
    public List<Tag> listTag() {
        List<Tag> tags = tagMapper.listTag();
        return tags;
    }
    @Transactional
    @Override
    public List<Tag> listTagByList(String ids) {
        List<Tag> tags = tagMapper.listTagByList(convertToList(ids));
        return tags;
    }
    public List<Long> convertToList(String ids){
        String[] arraySid = ids.split(",");
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null)
        {
            for(int i=0;i<arraySid.length;i++)
            {
                list.add(new Long(arraySid[i]));
            }
        }
        return list;
    }
    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagMapper.getTag(id);
        if(t == null){
            throw new NotFoundException("不存在该类型！");
        }
        BeanUtils.copyProperties(tag,t);
        return tagMapper.updateTag(t);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> getTagWithBlogsOrderTop(Integer size) {
        List<Tag> tags = tagMapper.getTagWithBlogsOrderTop();
        Collections.sort(tags, new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o2.getBlogs().size() - o1.getBlogs().size();
            }
        });
        if(size > tags.size())
            size = tags.size();
        List<Tag> sortedTags = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            sortedTags.add(tags.get(i));
        }
        return sortedTags;
    }
}
