package com.chaw.wong.controller;

import com.chaw.wong.entity.User;
import com.chaw.wong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "user/getUserById", method = {RequestMethod.POST, RequestMethod.GET})
    public User getUserById(@RequestBody User user) {
        return userService.getUserById(user);
    }

    @ResponseBody
    @RequestMapping(value = "user/insertSomeData", method = {RequestMethod.POST, RequestMethod.GET})
    public Boolean insertSomeData(@RequestBody User user) {
        user.setLevel(1);
        return userService.insertSomeData(user);
    }
}
