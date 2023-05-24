package com.example.diet.Controller;

import com.example.diet.Domain.*;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RecordService;
import com.example.diet.Service.UserService;
import com.example.diet.Util.JwtUtil;
import com.example.diet.Util.ObjectToMapConverter;
import io.jsonwebtoken.Claims;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userServcie;
    @Autowired
    private RecordService recordService;

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
            userServcie.InsertCal(userId,0,user.getReg_time());
            Float[] settings = {3.0f,5.0f,2.0f,3.0f,9.0f,6.0f};
            recordService.InsertSettings(userId,settings);   //设置默认的推荐参数
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




    @GetMapping("/record/recentdiet")
    public ResponseResult RecentDiet(@CurrentUserId String userId, @RequestParam(value = "date") String date){    //获取某天的饮食记录   还要有饮食热量  运动热量  今日步数
        DietRecord dietRecord = userServcie.RecordDiet(userId,date);
        Map<String,Object> temp = new HashMap<>();
        temp.put("diet_records",dietRecord);
        //查询 饮食热量
        Integer cal_num = recordService.getCal(userId,date);System.out.println(cal_num);
        Integer sport = userServcie.findSportByIdDate(userId,date);System.out.println(sport);
        Integer step = recordService.getStep(userId,date);  System.out.println(step);

        temp.put("cal_num",cal_num);
        temp.put("sport",sport);
        temp.put("step",step);
        System.out.println(dietRecord.toString());
        return new ResponseResult(200,temp);
    }


    @PostMapping("/update/recent")
    public ResponseResult UpdateRecentDiet(@CurrentUserId String userId, @RequestParam(value = "rec_name") String rec_name,
                                           @RequestParam(value = "cal_num") String cal_num, @RequestParam(value = "g_num") String g_num){    //近日饮食包含:  菜名 热量 克重

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
        recentDiet.setCal_num(Integer.parseInt(cal_num));
        recentDiet.setRec_name(rec_name);
        recentDiet.setG_num(Integer.parseInt(g_num));
        recentDiet.setRd_time(dateTime.substring(0,10));
        recentDiet.setRd_type(rec_type);
        recentDiet.setRd_usr(Integer.parseInt(userId));
        userServcie.InsertDiet(recentDiet);
        recordService.UpdateCal(userId,Long.parseLong(cal_num));     //更新卡路里摄入量
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


    @PostMapping("/updateinfo")  //更改注册信息
    public ResponseResult UpdateRegisterInfo(@CurrentUserId String userId, @RequestBody User user,
                                             @RequestParam (value = "allergen") String allergens, @RequestParam (value = "disease") String diseases) throws IllegalAccessException {
        user.setUsr_id(Long.parseLong(userId));
        System.out.println(user);
        userServcie.updateInfo(user);
        User user111 = userServcie.findById(userId);
        ArrayList<Map> res = new ArrayList<>();
        Map<String, Object> map = ObjectToMapConverter.convertObjectToMap(user111);
        res.add(map);
        recordService.updateAllergens(userId,allergens);
        recordService.updateDisease(userId,diseases);
        Map<String, Object> allergen = new HashMap<>();
        allergen.put("allergen",allergens);
        res.add(allergen);
        Map<String, Object> disease = new HashMap<>();
        disease.put("disease",diseases);
        res.add(disease);

        System.out.println("Update registration information successfully");
        return new ResponseResult(200,res);
    }


    public User findUserbyPhonenum(String userphone, String password) {
        System.out.println("User search by cell phone number");
        System.out.println(userphone);
        System.out.println(password);
        return userServcie.findUserbyPhonenum(userphone,password);
    }

}
