package com.chaw.wong.service;

import com.chaw.wong.dao.UserDAO;
import com.chaw.wong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public User getUserById(User user) {
        User res=userDAO.getUserById(user);
        return res;
    }

    @Transactional
    public Boolean insertSomeData(User user){

        return userDAO.insertSomeData(user);
    }

    @Transactional(readOnly = true)
    public List<Map<String,String>> getNotCommit(){
        List result=new ArrayList();
        List<User> user=userDAO.getNotCommit();
        for(int i=0;i<user.size();i++){
            Map a=new HashMap();
            a.put("name",user.get(i).getName());
            result.add(a);
        }
        return result;
    }

}
