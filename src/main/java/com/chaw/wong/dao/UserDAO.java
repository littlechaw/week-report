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
        return this.sessionFactory.openSession();
    }

//    private Session getSession() {
//        if (session == null) {
//            session = sessionFactory.openSession();
//        }
//        return session;
//    }

    public User getUserById(User user) {
        User res = (User) getSession().createQuery("from User where userId =? and password = ?").setParameter(0, user.getUserId()).setParameter(1, user.getPassword()).uniqueResult();
        return res;
    }

    public Boolean insertSomeData(User user) {
        String getsql = "from User where userId=?";
        User res = (User) getSession().createQuery(getsql).setParameter(0, user.getUserId()).uniqueResult();
        if (res != null) {
            return false;
        }
        String sql = "insert into user values('" + user.getUserId() + "','" + user.getPassword() + "','" + user.getName() + "'," + user.getTeam() + "," + user.getLevel() + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

}
