package com.by.mapper;

import com.by.po.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TypeMapper {
    int saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> listType();
    int updateType(Type type);
    void deleteType(Long id);
    List<Type> getTypeWithBlogsOrderTop();
}
