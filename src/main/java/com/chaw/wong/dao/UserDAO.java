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

    @SuppressWarnings("unused")
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public User getUserById(String id) {
        return (User) this.getSession().createQuery("from user where id =?").setParameter(0, id).uniqueResult();
    }


}
