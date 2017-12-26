package com.chaw.wong.dao;

import com.chaw.wong.entity.User;
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
        return (User) getSession().createQuery("from User where userId =? and password = ?").setParameter(0, user.getUserId()).setParameter(1,user.getPassword()).uniqueResult();
    }

}
