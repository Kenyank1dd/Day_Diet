package com.example.diet;

import com.example.diet.Mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<Map<String,Object>> users = userMapper.findFamilyMessagebyId(1);
        System.out.println(users);
        users = userMapper.findFamilyAllergenbyId(1);
        System.out.println(users);
    }
}
