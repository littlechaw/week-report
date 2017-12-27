package com.chaw.wong.service;

import com.chaw.wong.dao.WriteDAO;
import com.chaw.wong.entity.PlanWeekReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WriteService {
    @Autowired
    private WriteDAO writeDAO;

    @Transactional
    public Boolean insertReport(String id, Object report) {








        return writeDAO.insertReport(id, report);
    }

    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        return writeDAO.getByWeekNum(weekNum, id);
    }
}
