package com.example.diet;

import com.example.diet.Controller.TestController;
import com.example.diet.Mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testJunit() {
        System.out.println(1);
        System.out.println(userMapper.findAll());
    }
}
