package com.chaw.wong.dao;

import com.chaw.wong.entity.DoneWeekReport;
import com.chaw.wong.entity.ExtraWeekReport;
import com.chaw.wong.entity.PlanWeekReport;
import com.chaw.wong.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WriteDAO {
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

//    private Session getSession() {
//        if (session == null) {
//            session = sessionFactory.openSession();
//        }
//        return session;
//    }

    //插入到本周小结
    public Boolean insertDone(String id, String name, Object report, int weekNum) {
        String content = ((Map) report).get("content").toString();
        String planTime = ((Map) report).get("planTime").toString();
        String percent = ((Map) report).get("percent").toString();
        String doneTime = ((Map) report).get("doneTime").toString();
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_done(userId,name,content,planTime,percent,doneTime,remark,weekNum) " +
                "values('" + id + "','" + name + "','" + content + "','" + planTime + "','" + percent + "','" + doneTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertExtra(String id, String name, Object report, int weekNum) {
        String content = ((Map) report).get("content").toString();
        String usedTime = ((Map) report).get("usedTime").toString();
        String doneTime = ((Map) report).get("doneTime").toString();
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_extra(userId,name,content,usedTime,doneTime,remark,weekNum) " +
                "values('" + id + "','" + name + "','" + content + "','" + usedTime + "','" + doneTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertPlan(String id, String name, Object report, int weekNum) {
        String content = ((Map) report).get("content").toString();
        String planTime = ((Map) report).get("planTime").toString();
        String remark = (String) ((Map) report).get("remark");
        String sql = "insert into report_plan(userId,name,content,planTime,remark,weekNum) " +
                "values('" + id + "','" + name + "','" + content + "','" + planTime + "','" + remark + "'," + weekNum + ")";
        int num = getSession().createSQLQuery(sql).executeUpdate();
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    //删除本周重复提交的周报
    public void deleteReport(String id, int weekNum) {
        getSession().createQuery("delete from PlanWeekReport where userId=? and weekNum=?").setParameter(0, id).setParameter(1, weekNum).executeUpdate();
        getSession().createQuery("delete from ExtraWeekReport where userId=? and weekNum=?").setParameter(0, id).setParameter(1, weekNum).executeUpdate();
        getSession().createQuery("delete from DoneWeekReport where userId=? and weekNum=?").setParameter(0, id).setParameter(1, weekNum).executeUpdate();
    }

    //查询周报
    public Object selectLastWeek(String id, int weekNum) {
        String hql1 = "from PlanWeekReport where weekNum = ? and userId=?";
        String hql2 = "from DoneWeekReport where weekNum = ? and userId=?";
        String hql3 = "from ExtraWeekReport where weekNum = ? and userId=?";
        List res1 = getSession().createQuery(hql1).setParameter(0, weekNum + 1).setParameter(1, id).list();
        List res2 = getSession().createQuery(hql2).setParameter(0, weekNum).setParameter(1, id).list();
        List res3 = getSession().createQuery(hql3).setParameter(0, weekNum).setParameter(1, id).list();
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

    public Map getAllReport(String team, int weekNum) {
        Map result = new HashMap();
        int nextWeekNum = weekNum + 1;
        String sql = "select userId,name from user";
        String sql1 = "select * from report_done where userId in (select userId from user where team='" + team + "') and weekNum='" + weekNum + "'";
        String sql2 = "select * from report_extra where userId in (select userId from user where team='" + team + "') and weekNum='" + weekNum + "'";
        String sql3 = "select * from report_plan where userId in (select userId from user where team='" + team + "') and weekNum='" + nextWeekNum + "'";
        List<DoneWeekReport> res1 = getSession().createSQLQuery(sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<ExtraWeekReport> res2 = getSession().createSQLQuery(sql2).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<PlanWeekReport> res3 = getSession().createSQLQuery(sql3).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        result.put("doneObj", res1);
        result.put("extraObj", res2);
        result.put("planObj", res3);

        return result;
    }

}
