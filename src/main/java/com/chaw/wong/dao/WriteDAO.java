package com.chaw.wong.dao;

import com.chaw.wong.entity.PlanWeekReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public Boolean insertDone(String id, Object report, int weekNum) {
        String content = (String) ((Map) report).get("content");
        String planTime = (String) ((Map) report).get("planTime");
        int percent = (int) ((Map) report).get("percent");
        String doneTime = (String) ((Map) report).get("doneTime");
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_done(userId,content,planTime,percent,doneTime,remark,weekNum) " +
                "values('" + id + "','" + content + "','" + planTime + "'," + percent + ",'" + doneTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertExtra(String id, Object report, int weekNum) {
        String content = (String) ((Map) report).get("content");
        String usedTime = (String) ((Map) report).get("usedTime");
        String doneTime = (String) ((Map) report).get("doneTime");
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_extra(userId,content,usedTime,doneTime,remark,weekNum) " +
                "values('" + id + "','" + content + "','" + usedTime + "','" + doneTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertPlan(String id, Object report, int weekNum) {
        String content = (String) ((Map) report).get("content");
        String planTime = (String) ((Map) report).get("planTime");
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_plan(userId,content,planTime,remark,weekNum) " +
                "values('" + id + "','" + content + "','" + planTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        String hql = "from PlanWeekReport where weekNum = ? and userId=?";
        List<PlanWeekReport> res = getSession().createQuery(hql).setParameter(0, weekNum).setParameter(1, id).list();
        return res;
    }

}
