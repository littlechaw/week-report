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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class WriteController {
    @Autowired
    private WriteService writeService;

    @ResponseBody
    @RequestMapping(value = "get/getAllReport", method = {RequestMethod.POST, RequestMethod.GET})
    public Map getAllReport(@RequestBody String team) {
        Calendar c = Calendar.getInstance();
        int weekNum = c.get(Calendar.WEEK_OF_YEAR);
        if (team == null) {
            team = "1";
        }
        return writeService.getAllReport(team, weekNum-1);
    }


    @ResponseBody
    @RequestMapping(value = "get/getDayinWeek", method = {RequestMethod.POST, RequestMethod.GET})
    public List getFirstDayOfWeek() {
        List result = new ArrayList();
        Calendar cal = Calendar.getInstance();
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int n = cal.get(Calendar.DAY_OF_WEEK);
        if (n == 1) {
            n = 7;
        } else {
            n = n - 1;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i <= 7; i++) {
            cal.set(Calendar.DAY_OF_MONTH, date + i - n);
            result.add(sdf.format(cal.getTime()));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "get/getDayinNextWeek", method = {RequestMethod.GET, RequestMethod.POST})
    public List getDayinNextWeek() {
        List result = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = getWeekDays(1);
        try {
            for (int i = 0; i < 7; i++) {
                Calendar cd = Calendar.getInstance();
                cd.setTime(sdf.parse(date));
                cd.add(Calendar.DATE, i);
                String riqi = sdf.format(cd.getTime());
                result.add(i, riqi);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

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

    private static String getWeekDays(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // getTimeInterval(sdf);

        Calendar calendar1 = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayOfWeek) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 获得当前日期是一个星期的第几天
        int day = calendar1.get(Calendar.DAY_OF_WEEK);

        //获取当前日期前（下）x周同星几的日期
        calendar1.add(Calendar.DATE, 7 * i);

        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);


        String beginDate = sdf.format(calendar1.getTime());
//        calendar1.add(Calendar.DATE, 6);

//        String endDate = sdf.format(calendar1.getTime());

        return beginDate;
    }

}
