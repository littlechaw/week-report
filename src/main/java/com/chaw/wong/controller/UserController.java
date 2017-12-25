package com.chaw.wong.controller;

import com.chaw.wong.entity.User;
import com.chaw.wong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "user/getUserById", method = RequestMethod.GET)
    public User getUserById(String id) {
        return userService.getUserById(id);
    }
}
