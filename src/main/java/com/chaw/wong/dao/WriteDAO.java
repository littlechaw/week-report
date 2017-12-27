package com.chaw.wong.dao;

import com.chaw.wong.entity.DoneWeekReport;
import com.chaw.wong.entity.ExtraWeekReport;
import com.chaw.wong.entity.PlanWeekReport;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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

    //插入到本周小结
    public Boolean insertReport(String id, Object report) {
//        for(int i=0;i<report.;i++){
//
//        }
        return true;
    }


    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        String hql = "from PlanWeekReport where weekNum = ? and userId=?";
        List<PlanWeekReport> res = getSession().createQuery(hql).setParameter(0, weekNum).setParameter(1, id).list();
        return res;
    }

}
