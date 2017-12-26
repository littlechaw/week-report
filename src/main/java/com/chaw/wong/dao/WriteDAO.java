package com.chaw.wong.dao;

import com.chaw.wong.entity.DoneWeekReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class WriteDAO {
    @Resource
    private SessionFactory sessionFactory;
    private Session session;

    private Session getSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public Boolean insertThis(Object user, DoneWeekReport dwr) {
        String sql = "";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return true;
        }
    }

}
