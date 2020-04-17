package com.by.service;

import com.by.po.Type;

import java.util.List;

public interface TypeService {
    int saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> listType();
    int updateType(Long id,Type type);
    void deleteType(Long id);
    //查询type及其blogs
    List<Type> getTypeWithBlogsOrderTop(Integer size);
}
