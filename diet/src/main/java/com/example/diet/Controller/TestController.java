package com.example.diet.Controller;

import com.example.diet.Resolver.CurrentUserId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @PostMapping("/hello")
    public void CollectArticle(@CurrentUserId String userId, @RequestBody List<String> ings){
        System.out.println("user_id = ");
        System.out.println(userId);
        for (String ing : ings){
            System.out.println(ing);
        }
        System.out.println("success");
    }
}
