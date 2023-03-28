package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.*;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @InvokeLog
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @InvokeLog
    public List<Recipe> SearchRecipe(String rec_id) {
        return userMapper.SearchRecipe(rec_id);
    }
    @Override
    @InvokeLog
    public Integer RecordWater(String userId){
        
    }
    @Override
    @InvokeLog
    public Integer UpdateWater(String userId){

    }
    @Override
    @InvokeLog
    public List<UsrFamily> GetFamily(String userId){

    }
    @Override
    @InvokeLog
    public DietRecord RecordDiet(String userId, String date){

    }
    @Override
    @InvokeLog
    public List<Integer> GetWeight(String userId){

    }
    @Override
    @InvokeLog
    public Post_User GetPost(String userId){

    }
    @Override
    @InvokeLog
    public List<Article> GetArticle(String userId){

    }
    @Override
    @InvokeLog
    public List<Article> GetCollectArticle(String userId){

    }
    @Override
    @InvokeLog
    public void CollectArticle(String userId,String article_title){

    }
}
