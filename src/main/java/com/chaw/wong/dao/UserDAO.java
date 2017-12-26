package com.chaw.wong.dao;

import com.chaw.wong.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDAO {

    @Resource
    private SessionFactory sessionFactory;
    private Session session;

    private Session getSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public User getUserById(User user) {
        return (User) getSession().createQuery("from User where userId =? and password = ?").setParameter(0, user.getUserId()).setParameter(1, user.getPassword()).uniqueResult();
    }

    public Boolean insertSomeData(User user) {
        String sql = "insert into user values('" + user.getUserId() + "','" + user.getPassword() + "','" + user.getName() + "'," + user.getTeam() + "," + user.getLevel() + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return true;
        }
    }

}
