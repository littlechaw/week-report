package com.chaw.wong.service;

import com.chaw.wong.dao.UserDAO;
import com.chaw.wong.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;


    public User getUserById(User user) {
        return userDAO.getUserById(user);
    }

    public Boolean insertSomeData(User user){

        return userDAO.insertSomeData(user);
    }


}
