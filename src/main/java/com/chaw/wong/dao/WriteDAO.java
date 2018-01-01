package com.chaw.wong.dao;

import com.chaw.wong.entity.DoneWeekReport;
import com.chaw.wong.entity.ExtraWeekReport;
import com.chaw.wong.entity.PlanWeekReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
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
        String percent = (String) ((Map) report).get("percent");
        String doneTime = (String) ((Map) report).get("doneTime");
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_done(userId,content,planTime,percent,doneTime,remark,weekNum) " +
                "values('" + id + "','" + content + "','" + planTime + "','" + percent + "','" + doneTime + "','" + remark + "'," + weekNum + ")";
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

    //查询周报
    public Object selectLastWeek(String id, int weekNum) {
        String hql1 = "from PlanWeekReport where weekNum = ? and userId=?";
        String hql2 = "from DoneWeekReport where weekNum = ? and userId=?";
        String hql3 = "from ExtraWeekReport where weekNum = ? and userId=?";
        List<PlanWeekReport> res1 = getSession().createQuery(hql1).setParameter(0, weekNum+1).setParameter(1, id).list();
        List<DoneWeekReport> res2 = getSession().createQuery(hql2).setParameter(0, weekNum).setParameter(1, id).list();
        List<ExtraWeekReport> res3 = getSession().createQuery(hql3).setParameter(0, weekNum).setParameter(1, id).list();
        Map map = new HashMap();
        map.put("planObj", res1);
        map.put("doneObj", res2);
        map.put("extraObj", res3);
        return map;
    }

    public List<PlanWeekReport> getByWeekNum(int weekNum, String id) {
        String hql = "from PlanWeekReport where weekNum = ? and userId=?";
        List<PlanWeekReport> res = getSession().createQuery(hql).setParameter(0, weekNum).setParameter(1, id).list();
        return res;
    }

}
