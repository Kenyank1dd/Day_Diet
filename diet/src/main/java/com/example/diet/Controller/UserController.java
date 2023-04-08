package com.example.diet.Controller;

import com.example.diet.Domain.*;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.UserService;
import com.example.diet.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userServcie;

    @GetMapping("/register")
    public ResponseResult Register(@RequestBody RegisterInfo registerInfo){
        boolean isRegistered = userServcie.isRegister(registerInfo.getUsr_phone());
        if(!isRegistered){    //未注册  在用户列表中添加
            User user = new User();
            user.setUsr_name(registerInfo.getUsr_name());
            user.setUsr_age(registerInfo.getUsr_age());
            user.setUsr_password(registerInfo.getUsr_password());
            user.setUsr_phone(registerInfo.getUsr_phone());
            user.setUsr_sex(registerInfo.isUsr_sex());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            user.setReg_time(formatter.format(date));
            userServcie.InsertUser(user);
            return new ResponseResult(200,"注册成功");
        }
        else if(isRegistered){
            return new ResponseResult(201,"用户已注册");
        }
        return null;
    }

    @GetMapping("/login")
    public ResponseResult Login(@RequestParam (value = "user_phone") String user_phone,@RequestParam (value = "user_password") String user_password) throws Exception {
        //找到usr_id
        RegisterInfo userinfo = findUserbyPhonenum(user_phone,user_password);
        System.out.println(userinfo.getUsr_phone());
        if(userinfo != null) {
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), userinfo.getUsr_phone(), null);
            System.out.println(token);
            Claims claims = JwtUtil.parseJWT(token);
            String subject = claims.getSubject();
            userinfo.setUsr_phone(subject);
            return new ResponseResult(200, "success", userinfo);
        }
        else {
            return new ResponseResult(201,"密码或账号错误");
        }
    }

    @RequestMapping("/findAll")
    public ResponseResult findAll(@CurrentUserId String userId) throws Exception {
        System.out.println(userId);
        //调用service查询数据 ，进行返回s
        List<User> users = userServcie.findAll();

        return new ResponseResult(200,users);
    }

    @GetMapping("/record/water")
    public ResponseResult RecordWater(@CurrentUserId String userId){
        Integer water = userServcie.RecordWater(userId);
        return new ResponseResult(200,water);
    }

    @PutMapping ("/update/water")
    public ResponseResult UpdateWater(@CurrentUserId String userId,@RequestParam("water_num") long water_num){
        Integer water = userServcie.UpdateWater(userId,water_num);
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
        List<WeightRecord> weights = userServcie.GetWeight(userId);
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

    public RegisterInfo findUserbyPhonenum(String userphone, String password) {
        return userServcie.findUserbyPhonenum(userphone,password);
    }
}
