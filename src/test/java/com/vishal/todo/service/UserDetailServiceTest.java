package com.vishal.todo.service;

import com.vishal.todo.model.UserDetail;
import org.junit.jupiter.api.Test;

public class UserDetailServiceTest {
    @Test
    void saveTest() {
        UserDetailService userDetailService = new UserDetailService();
        UserDetail userDetail = new UserDetail("test@gmail.com", "Test", "Test", "test");
        userDetailService.createUser(userDetail);
    }
}
