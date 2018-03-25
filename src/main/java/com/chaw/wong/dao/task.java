package com.chaw.wong.dao;

import com.chaw.wong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class task {
    @Autowired
    private UserService userService;
    @Scheduled(cron = "0 0 2 * * ?")
    void doSomeThing(){
        userService.updateStatusNoCon();
    }
}
