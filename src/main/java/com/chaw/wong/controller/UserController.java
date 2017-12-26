package com.chaw.wong.controller;

import com.chaw.wong.entity.User;
import com.chaw.wong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "user/getUserById", method = {RequestMethod.POST, RequestMethod.GET})
    public Boolean getUserById(@RequestBody User user, HttpServletRequest request) {
        User res = userService.getUserById(user);
        //保存session
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", res);
        Boolean bres = false;
        if (res.getName() != null) {
            bres = true;
        }
        return bres;
    }

    @ResponseBody
    @RequestMapping(value = "user/insertSomeData", method = {RequestMethod.POST, RequestMethod.GET})
    public Boolean insertSomeData(@RequestBody User user) {
        user.setLevel(1);
        return userService.insertSomeData(user);
    }
}
