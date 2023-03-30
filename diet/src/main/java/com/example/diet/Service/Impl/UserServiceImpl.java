package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.*;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Integer RecordWater(String userId){
        String date = userMapper.GetDayWater(userId);
        long nowMillis = System.currentTimeMillis();
        DateTime now = new DateTime(nowMillis);
        String today = now.toString();
        if(!today.substring(0,9).equals(date.substring(0,9))){   //如果不相等要更新     如果日期相等 则直接获得
            userMapper.SetZero(userId);    //更新喝水量为0
            userMapper.UpdateDayWater(userId,today);  //更新为今天的日期
            return 0;   //直接返回0 即可
        }
        return userMapper.RecordWater(userId);   //获取喝水量
    }

    @Override
    @InvokeLog
    public Integer UpdateWater(String userId,long water_num){
        userMapper.UpdateWater(userId,water_num);
        return userMapper.RecordWater(userId);
    }

    @Override
    @InvokeLog
    public List<UsrFamily> GetFamily(String userId){
        return userMapper.GetFamily(userId);
    }

    @Override
    @InvokeLog
    public DietRecord RecordDiet(String userId, String date){
        List<Recent_diet> breaklist = userMapper.GetBreakDiet(userId);   //取所有的该用户早餐记录
        List<Recipe> breakRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : breaklist){
            if(temp.getRd_time().substring(0,9).equals(date.substring(0,9))){
                breakRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }

        List<Recent_diet> lunchlist = userMapper.GetLunchDiet(userId);   //取所有的该用户午餐记录
        List<Recipe> lunchRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : lunchlist){
            if(temp.getRd_time().substring(0,9).equals(date.substring(0,9))){
                lunchRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }

        List<Recent_diet> dinnerlist = userMapper.GetDinnerDiet(userId);   //取所有的该用户晚餐记录
        List<Recipe> dinnerRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : dinnerlist){
            if(temp.getRd_time().substring(0,9).equals(date.substring(0,9))){
                dinnerRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }
        DietRecord result = new DietRecord(breakRecipe,lunchRecipe,dinnerRecipe);
        return result;
    }

    @Override
    @InvokeLog
    public List<Integer> GetWeight(String userId){
        return userMapper.GetWeight(userId);
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
