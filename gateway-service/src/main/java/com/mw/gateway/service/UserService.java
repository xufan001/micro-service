package com.mw.gateway.service;

import com.mw.gateway.entity.User;

import java.util.List;

public interface UserService {


    List<User> getUserByQuery(User user);


    User getUserByEmail(String email);
}
