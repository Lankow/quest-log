package com.lankovv.questlog.service;


import com.lankovv.questlog.model.User;

public interface UserService {

    User findUserByEmail(String email) ;
    User saveUser(User user);
}
