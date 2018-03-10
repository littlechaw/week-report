package com.chaw.wong.service;

import com.chaw.wong.dao.UserDAO;
import com.chaw.wong.dao.WriteDAO;
import com.chaw.wong.entity.PlanWeekReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class WriteService {
    @Autowired
    private WriteDAO writeDAO;
    @Autowired
    private UserDAO userDAO;

    @Transactional
    public Boolean insertReport(String id, String name, Map report) {

        List dwrList = (List) report.get("doneObj");
        List ewrList = (List) report.get("extraObj");
        List pwrList = (List) report.get("planObj");
        Calendar c = Calendar.getInstance();
        int weekNum = c.get(Calendar.WEEK_OF_YEAR);
        writeDAO.deleteReport(id, weekNum);
        for (Object d : dwrList) {
            if (((Map) d).get("content") != null && ((Map) d).get("content") != "")
                writeDAO.insertDone(id, name, d, weekNum);
        }
        for (Object e : ewrList) {
            if (((Map) e).get("content") != null && ((Map) e).get("content") != "")
                writeDAO.insertExtra(id, name, e, weekNum);
        }
        for (Object p : pwrList) {
            if (((Map) p).get("content") != null && ((Map) p).get("content") != "")
                writeDAO.insertPlan(id, name, p, weekNum + 1);
        }
        userDAO.updateStatus(1,id);
        return true;
    }

    @Transactional
    public Map getAllReport(String team, int weekNum) {
        return writeDAO.getAllReport(team, weekNum);
    }

    @Transactional
    public Object selectLastWeek(String id, int weekNum) {
        return writeDAO.selectLastWeek(id, weekNum);
    }

    @Transactional
    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        return writeDAO.getByWeekNum(weekNum, id);
    }
}
