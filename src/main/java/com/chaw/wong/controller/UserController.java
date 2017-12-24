package com.chaw.wong.controller;

import com.chaw.wong.entity.User;
import com.chaw.wong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public User getUserById(String id) {
        return userService.getUserById(id);
    }
}
