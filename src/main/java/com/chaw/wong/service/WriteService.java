package com.chaw.wong.service;

import com.chaw.wong.dao.WriteDAO;
import com.chaw.wong.entity.DoneWeekReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteService {
    @Autowired
    private WriteDAO writeDAO;

    public Boolean insertThis(Object user, DoneWeekReport dwr) {
        writeDAO.insertThis(user,dwr);
        return false;
    }
}
