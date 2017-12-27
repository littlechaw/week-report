package com.chaw.wong.service;

import com.chaw.wong.dao.WriteDAO;
import com.chaw.wong.entity.DoneWeekReport;
import com.chaw.wong.entity.PlanWeekReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriteService {
    @Autowired
    private WriteDAO writeDAO;

    public Boolean insertThis(Object user, DoneWeekReport dwr) {
        writeDAO.insertThis(user, dwr);
        return false;
    }

    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        List<PlanWeekReport> res = writeDAO.getByWeekNum(weekNum, id);
        return res;
    }
}
