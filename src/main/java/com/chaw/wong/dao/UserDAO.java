package com.chaw.wong.dao;

import com.chaw.wong.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDAO {

    @Resource
    private SessionFactory sessionFactory;

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

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
        String sql = "insert into user(userId,password,name,team,level) values('" + user.getUserId() + "','" + user.getPassword() + "','" + user.getName() + "'," + user.getTeam() + "," + user.getLevel() + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateStatus(int status, String id) {
        String hql = "update User set status=? where userId=?";
        getSession().createQuery(hql).setParameter(0, status).setParameter(1, id).executeUpdate();
    }

    public void updateStatusNoCon() {
        String hql = "update User set status=?";
        int num=getSession().createQuery(hql).setParameter(0,0).executeUpdate();
    }

    public List getNotCommit() {
        String hql = "from User where status=0 order by team asc";
        return getSession().createQuery(hql).list();
    }

}
