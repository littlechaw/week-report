package com.chaw.wong.controller;

import com.chaw.wong.entity.PlanWeekReport;
import com.chaw.wong.entity.User;
import com.chaw.wong.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class WriteController {
    @Autowired
    private WriteService writeService;

    @ResponseBody
    @RequestMapping(value = "write/insertThis", method = {RequestMethod.POST, RequestMethod.GET})
    public Boolean insertThis(@RequestBody Map report, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = ((User) session.getAttribute("userInfo")).getUserId();
        return writeService.insertReport(id, report);

    }

    @ResponseBody
    @RequestMapping(value = "get/getDwr", method = {RequestMethod.GET, RequestMethod.POST})
    public List<PlanWeekReport> getByWeekNum(HttpServletRequest request) {
        Calendar c = Calendar.getInstance();
        HttpSession session = request.getSession();
        int weekNum = c.get(Calendar.WEEK_OF_YEAR);
        String id = ((User) session.getAttribute("userInfo")).getUserId();
        List<PlanWeekReport> result = writeService.getByWeekNum(weekNum, id);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "get/getMyReport", method = {RequestMethod.GET, RequestMethod.POST})
    public Object selectLastWeek(HttpServletRequest request) {
        Calendar c = Calendar.getInstance();
        HttpSession session = request.getSession();
        int weekNum = c.get(Calendar.WEEK_OF_YEAR);
        String id = ((User) session.getAttribute("userInfo")).getUserId();
        return writeService.selectLastWeek(id, weekNum);
    }
}
