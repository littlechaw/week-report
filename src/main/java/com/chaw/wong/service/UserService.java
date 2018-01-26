package com.chaw.wong.service;

import com.chaw.wong.dao.UserDAO;
import com.chaw.wong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


}
