package com.by.service;

import com.by.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
