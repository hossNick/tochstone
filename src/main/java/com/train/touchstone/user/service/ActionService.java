package com.train.touchstone.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import static com.train.touchstone.auth.SecurityUtils.getCurrentUser;

@Service
@Slf4j
public class ActionService {

    public String  callAction(){
        try {
            action();
            return getCurrentUser().get().getEmail();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private synchronized void action() throws InterruptedException {
        Thread.sleep(10000);
    }
}
