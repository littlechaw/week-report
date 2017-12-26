package com.chaw.wong.controller;

import com.chaw.wong.entity.DoneWeekReport;
import com.chaw.wong.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class WriteController {
    @Autowired
    private WriteService writeService;

    @ResponseBody
    @RequestMapping(value = "write/insertThis", method = {RequestMethod.POST, RequestMethod.GET})
    public Object insertThis(@RequestBody DoneWeekReport dwr, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object user=session.getAttribute("userInfo");
        Boolean res=writeService.insertThis(user,dwr);

        return user;
    }
}
