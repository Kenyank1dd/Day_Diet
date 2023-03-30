package com.example.diet.Mapper;

import com.example.diet.Domain.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();
    public Integer RecordWater(String userId);    //获取喝水量
    public String GetDayWater(String userId);  //获取喝水表的日期
    public void SetZero(String userId);  //将喝水量置零
    public void UpdateDayWater(String userId,String today);  //更新喝水表的日期为今天
    public void UpdateWater(String userId,long water_num);   //更新喝水量
    public List<UsrFamily> GetFamily(String userId);   //获取家庭成员
    public List<Recent_diet> GetBreakDiet(String userId);  //获取今日早餐饮食记录
    public List<Recent_diet> GetLunchDiet(String userId);  //获取今日午餐饮食记录
    public List<Recent_diet> GetDinnerDiet(String userId);  //获取今日晚餐饮食记录
    public Recipe GetRecipe(long recipe_id);  //获取菜谱的详细信息

//    public DietRecord RecordDiet(String userId, String date);
    public List<Integer> GetWeight(String userId);

    public Post_User GetPost(String userId);
    public List<Article> GetArticle(String userId);
    public List<Article> GetCollectArticle(String userId);
    public void CollectArticle(String userId,String article_title);



    @MapKey("family_id")
    List<Map<String,Object>> findFamilyMessagebyId(Integer userId);
    @MapKey("family_id")
    List<Map<String,Object>> findFamilyAllergenbyId(Integer userId);


}
