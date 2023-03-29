package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.*;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

        return null;
    }

    @Override
    @InvokeLog
    public Integer UpdateWater(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public List<UsrFamily> GetFamily(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public DietRecord RecordDiet(String userId, String date){
        return null;
    }

    @Override
    @InvokeLog
    public List<Integer> GetWeight(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public Post_User GetPost(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public List<Article> GetArticle(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public List<Article> GetCollectArticle(String userId){
        return null;
    }

    @Override
    @InvokeLog
    public void CollectArticle(String userId,String article_title){

    }

    @Override
    @InvokeLog
    public List<Map<String,Object>> findFamilyMessagebyId(Integer userId) {
        return userMapper.findFamilyMessagebyId(userId);
    }

    @Override
    @InvokeLog
    public List<Map<String,Object>> findFamilyAllergenbyId(Integer userId) {
        return userMapper.findFamilyAllergenbyId(userId);
    }
}
