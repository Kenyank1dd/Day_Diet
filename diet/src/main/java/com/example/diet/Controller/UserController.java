package com.example.diet.Controller;

import com.example.diet.Domain.*;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userServcie;

    @RequestMapping("/findAll")
    public ResponseResult findAll(@CurrentUserId String userId) throws Exception {
        System.out.println(userId);
        //调用service查询数据 ，进行返回s
        List<User> users = userServcie.findAll();

        return new ResponseResult(200,users);
    }

    @GetMapping("/search/recipe")
    public ResponseResult SearchRecipe(@RequestParam (value = "rec_id") String rec_id) throws Exception{   //rec_id对应前端url路径里的参数名称
        List<Recipe> recipes = userServcie.SearchRecipe(rec_id);
        return new ResponseResult(200,recipes);
    }

    @GetMapping("/record/water")
    public ResponseResult RecordWater(@CurrentUserId String userId){
        Integer water = userServcie.RecordWater(userId);
        return new ResponseResult(200,water);
    }

    @PutMapping ("/update/water")
    public ResponseResult UpdateWater(@CurrentUserId String userId){
        Integer water = userServcie.UpdateWater(userId);
        return new ResponseResult(200,water);
    }

    @GetMapping("/record/family")
    public ResponseResult GetFamily(@CurrentUserId String userId){
        List<UsrFamily> families = userServcie.GetFamily(userId);
        return new ResponseResult(200,families);
    }

    @GetMapping("/record/recentdiet")
    public ResponseResult RecentDiet(@CurrentUserId String userId , @RequestParam(value = "date") String date){    //获取某天的饮食记录
        DietRecord dietRecord = userServcie.RecordDiet(userId,date);
        return new ResponseResult(200,dietRecord);
    }

    @GetMapping("/record/weight")
    public ResponseResult GetWeight(@CurrentUserId String userId){
        List<Integer> weights = userServcie.GetWeight(userId);
        return new ResponseResult(200,weights);
    }

    @GetMapping("/community/interest")
    public ResponseResult GetPost(@CurrentUserId String userId){
        Post_User pu = userServcie.GetPost(userId);
        return new ResponseResult(200,pu);
    }

    @GetMapping("/community/article")
    public ResponseResult GetArticle(@CurrentUserId String userId){   //获取推荐文章
        List<Article> articles = userServcie.GetArticle(userId);
        return new ResponseResult(200,articles);
    }

    @GetMapping("/collect/article")
    public ResponseResult GetCollectArticle(@CurrentUserId String userId){  //获取收藏文章
        List<Article> articles = userServcie.GetCollectArticle(userId);
        return new ResponseResult(200,articles);
    }

    @PostMapping("/collect/article")
    public ResponseResult CollectArticle(@CurrentUserId String userId,@RequestParam(value = "article_title") String article_title){
        userServcie.CollectArticle(userId,article_title);
        return new ResponseResult(200,"");
    }

    public List<Map<String,Object>> findFamilyMessagebyId(Integer userId) {
        return userServcie.findFamilyMessagebyId(userId);
    }

    public List<Map<String,Object>> findFamilyAllergenbyId(Integer userId) {
        return userServcie.findFamilyAllergenbyId(userId);
    }
}
