package com.by.mapper;

import com.by.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper//表示这是mybatis的mapper类
@Repository//将该类注入容器
public interface UserMapper {
    User checkUser(String username, String password);
}
