package com.example.diet.Service;

import com.example.diet.Domain.*;
import com.example.diet.Service.Impl.RecipeServieImpl;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public List<Recipe> SearchRecipe(String rec_id);

    public Integer RecordWater(String userId);

    public Integer UpdateWater(String userId);
    public List<UsrFamily> GetFamily(String userId);

    public DietRecord RecordDiet(String userId,String date);
    public List<Integer> GetWeight(String userId);

    public Post_User GetPost(String userId);
    public List<Article> GetArticle(String userId);
    public List<Article> GetCollectArticle(String userId);
    public void CollectArticle(String userId,String article_title);


}
