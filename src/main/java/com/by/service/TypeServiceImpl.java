package com.by.service;

import com.by.exception.NotFoundException;
import com.by.mapper.TypeMapper;
import com.by.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        int n = typeMapper.saveType(type);
        return n;
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        Type type = typeMapper.getType(id);
        return type;
    }

    @Override
    public Type getTypeByName(String name) {
        Type type = typeMapper.getTypeByName(name);
        return type;
    }

    @Transactional
    @Override
    public List<Type> listType() {
        List<Type> types = typeMapper.listType();
        return types;
    }
    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeMapper.getType(id);
        if(t == null){
            throw new NotFoundException("不存在该类型！");
        }
        BeanUtils.copyProperties(type,t);
        return typeMapper.updateType(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public List<Type> getTypeWithBlogsOrderTop(Integer size) {
        List<Type> types = typeMapper.getTypeWithBlogsOrderTop();
        Collections.sort(types, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBlogs().size() - o1.getBlogs().size();
            }
        });
        if(size > types.size()){
            size = types.size();
        }
        List<Type> sortedTypes = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            sortedTypes.add(types.get(i));
        }
        return sortedTypes;
    }
}
