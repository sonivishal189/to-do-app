package com.vishal.todo.service;

import com.vishal.todo.dao.UserDetailDao;
import com.vishal.todo.model.UserDetail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailService {

    private final UserDetailDao userDetailDao = new UserDetailDao();

    public void createUser(UserDetail userDetail) {
        log.info("Creating user");
        userDetailDao.save(userDetail);
    }

    public UserDetail getUserById(String userId) {
        log.info("Get user for userId {}", userId);
        return userDetailDao.findById(userId);
    }
}
