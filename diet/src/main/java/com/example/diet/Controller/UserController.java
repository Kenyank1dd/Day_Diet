package com.example.diet.Controller;

import com.example.diet.Domain.*;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.UserService;
import com.example.diet.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("/checktoken")
    public ResponseResult check(@CurrentUserId String userId) {

        User user = userServcie.findById(userId);
        if(user == null) {
            System.out.println("the token sent to Server is wrong!");
            return new ResponseResult(201,"token错误");
        }
        else {
            System.out.println("the token sent to Server is right!");
            System.out.println(user);
            return new ResponseResult(200,user);
        }
    }

    @PostMapping("/register")
    public ResponseResult Register(@RequestBody User user){
        System.out.println(user.getUsr_phone());
        boolean isRegistered = userServcie.isRegister(user.getUsr_phone());
        System.out.println(user);
        if(!isRegistered){    //未注册  在用户列表中添加
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            user.setReg_time(formatter.format(date));
            userServcie.InsertUser(user);
            long userId = userServcie.getUserId(user.getUsr_phone());
            userServcie.InsertWater(userId,0,user.getReg_time());
            System.out.println("user is inserted successfully!");
            return new ResponseResult(200,"注册成功");
        }
        else {
            System.out.println("user is already bean registered");
            return new ResponseResult(201,"用户已注册");
        }
    }

    @GetMapping("/login")
    public ResponseResult Login(@RequestParam (value = "user_phone") String user_phone,@RequestParam (value = "user_password") String user_password) throws Exception {
        //找到usr_id
        User userinfo = findUserbyPhonenum(user_phone,user_password);
        System.out.println(userinfo);
        System.out.println(userinfo.getUsr_phone());
        if(userinfo != null) {
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), String.valueOf(userinfo.getUsr_id()), null);
            System.out.println(token);
            userinfo.setUsr_phone(token);
            System.out.println("login successfully!");
            return new ResponseResult(200, "success", userinfo);
        }
        else {
            System.out.println("Wrong account or password!");
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
        System.out.println(water);
        System.out.println("Checking today's water intake successfully!");
        return new ResponseResult(200,water);
    }

    @PostMapping ("/update/water")
    public ResponseResult UpdateWater(@CurrentUserId String userId,@RequestParam("water_num") long water_num){
        Integer water = userServcie.UpdateWater(userId,water_num);
        System.out.println(water);
        System.out.println("Update Today's Drinking Water Successfully!");
        return new ResponseResult(200,water);
    }

    @GetMapping("/record/family")
    public ResponseResult GetFamily(@CurrentUserId String userId){
        List<UsrFamily> families = userServcie.GetFamily(userId);
        for(UsrFamily usrFamily : families) {
            System.out.println(usrFamily);
        }
        System.out.println("Obtain user family member information successfully!");
        return new ResponseResult(200,families);
    }


    @GetMapping("/record/recentdiet")
    public ResponseResult RecentDiet(@CurrentUserId String userId, String date){    //获取某天的饮食记录
        DietRecord dietRecord = userServcie.RecordDiet(userId,date);
        System.out.println(dietRecord.toString());
        System.out.println("Get the user's recent diet information successfully!");
        return new ResponseResult(200,dietRecord);
    }

    @PostMapping("/get_dis_all")   //获取家庭用户的疾病和过敏源
    public ResponseResult Get_dis_all(@CurrentUserId String userId){
        List<FamilyInfo> familyInfos = userServcie.Get_dis_all(userId);
        if(familyInfos == null){
            System.out.println();
            System.out.println("User did not add family member information, query failed!");
            return new ResponseResult(200,"该用户未添加家庭成员！");
        }
        else{
            for(FamilyInfo familyInfo : familyInfos) {
                System.out.println(familyInfo);
            }
            System.out.println("Return family member information successfully!");
            return new ResponseResult(200,familyInfos);
        }
    }


    @PostMapping("/update_dis_all")  //修改家庭成员的疾病和过敏源
    public ResponseResult Update_dis_all(@CurrentUserId String userId,@RequestBody FamilyInfo familyInfo){
        List<FamilyInfo> familyInfos = userServcie.GetFamilyInfo(userId,familyInfo.getRelation());
        for(FamilyInfo familyInfo1 : familyInfos) {
            System.out.println(familyInfo1);
        }
        familyInfo.setUsr_id(Long.parseLong(userId));   //记得把user_id插入
        if(familyInfos.size() == 0){   //还没有添加的话insert
            System.out.println("Insert family member information successfully!");
            userServcie.InsertFamily(familyInfo);   //先插入

            List<FamilyInfo> f = userServcie.Get_dis_all(userId);  //再查询
            for(FamilyInfo familyInfo1 : f) {
                System.out.println(familyInfo1);
            }
            return new ResponseResult(200,"更新成功",f);
        }
        else {    //已经添加的话update
            userServcie.UpdateFamily(familyInfo);  //先更新

            List<FamilyInfo> f = userServcie.Get_dis_all(userId);    //再查询
            for(FamilyInfo familyInfo1 : f) {
                System.out.println(familyInfo1);
            }
            return new ResponseResult(200,"更新成功",f);

        }
    }


    @PostMapping("/update/recent")
    public ResponseResult UpdateRecentDiet(@CurrentUserId String userId, @RequestParam(value = "rec_name") String rec_name,
                                           @RequestParam(value = "cal_num") String cal_num){
        long rec_id = 0;
        rec_id = userServcie.GetRecipeId(rec_name);
        if(rec_id == 0) {
            System.out.println("No dish name found!");
            return new ResponseResult<>(201,"添加失败,不存在该菜品");
        }
        String dateTime = DateTime.now().toString();
        String day = dateTime.substring(0,10);
        String hour = dateTime.substring(11,13);
        int hour2 =  Integer.parseInt(hour);
        System.out.println(hour2);
        long rec_type = 0;
        if(hour2 <= 10 && hour2 >= 0) rec_type = 1;
        else if(hour2 <= 15 && hour2 >= 10) rec_type = 2;
        else if(hour2 <= 24 && hour2 >= 15) rec_type = 3;
        Recent_diet recentDiet = new Recent_diet();
        recentDiet.setRd_rec(rec_id);
        recentDiet.setRd_time(dateTime.substring(0,10));
        recentDiet.setRd_type(rec_type);
        recentDiet.setRd_usr(Integer.parseInt(userId));
        userServcie.InsertDiet(recentDiet);
        userServcie.UpdateCal(userId,day,cal_num);
        System.out.println("Add today's diet record successfully");
        return new ResponseResult<>(200,"添加今日饮食记录成功");
    }

    @GetMapping("/record/weight")
    public ResponseResult GetWeight(@CurrentUserId String userId){
        List<WeightRecord> weights = userServcie.GetWeight(userId);
        for(WeightRecord weightRecord : weights) {
            System.out.println(weightRecord);
        }
        System.out.println("Add weight record successfully!");
        return new ResponseResult(200,weights);
    }

    @PostMapping("/update/weight")
    public ResponseResult UpdateWeight(@CurrentUserId String userId, @RequestParam(value = "weight") String weight) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        System.out.println(time);
        Float realweight = Float.parseFloat(weight);
        WeightRecord weightRecord = userServcie.findUserbyDateId(userId,time);
        System.out.println(weightRecord);
        if(weightRecord == null) {
            userServcie.InsertWeight(userId,realweight,time);
            List<WeightRecord> weights = userServcie.GetWeight(userId);
            for(WeightRecord weightRecord1 : weights) {
                System.out.println(weightRecord1);
            }
            System.out.println("Update weight record successfully!");
            return new ResponseResult(200,weights);
        }
        else {
            userServcie.UpdateWeight(userId,realweight,time);
            List<WeightRecord> weights = userServcie.GetWeight(userId);
            for(WeightRecord weightRecord1 : weights) {
                System.out.println(weightRecord1);
            }
            return new ResponseResult(200,weights);
        }
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

    @PostMapping("/updateinfo")  //更改注册信息
    public ResponseResult UpdateRegisterInfo(@CurrentUserId String userId, @RequestBody User user){
        user.setUsr_id(Long.parseLong(userId));
        System.out.println(user);
        userServcie.updateInfo(user);
        User user111 = userServcie.findById(userId);
        System.out.println("Update registration information successfully");
        return new ResponseResult(200,user111);
    }

    public List<Map<String,Object>> findFamilyMessagebyId(Integer userId) {
        System.out.println("Query family member information by user id");
        System.out.println(userId);
        return userServcie.findFamilyMessagebyId(userId);
    }

    public List<Map<String,Object>> findFamilyAllergenbyId(Integer userId) {
        return userServcie.findFamilyAllergenbyId(userId);
    }

    public User findUserbyPhonenum(String userphone, String password) {
        System.out.println("User search by cell phone number");
        System.out.println(userphone);
        System.out.println(password);
        return userServcie.findUserbyPhonenum(userphone,password);
    }

    public List<FamilyInfo> findFamilyByusrId(String usrId) {
        System.out.println("Query family member information by user id!");
        System.out.println(usrId);
        return userServcie.findFamilyByusrId(usrId);
    }

    @PostMapping("/hahaha")
    public void shuchu(@RequestBody Map map) {
        System.out.println(map.get("content"));
    }
}
