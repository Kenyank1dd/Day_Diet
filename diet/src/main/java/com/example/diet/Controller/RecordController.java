package com.example.diet.Controller;

import com.example.diet.Domain.ResponseResult;
import com.example.diet.Domain.User;
import com.example.diet.Domain.UsrFamily;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RecordService;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class RecordController {

    @Autowired
    private RecordService recordServcie;

    @Autowired
    private UserService userServcie;

    @GetMapping("/record/cal")
    public ResponseResult RecordCal(@CurrentUserId String userId){     //获得今日摄入卡路里
        Integer cal = recordServcie.RecordCal(userId);
        System.out.println(cal);
        System.out.println("Checking today's cal intake successfully!");
        return new ResponseResult(200,cal);
    }


    @PostMapping("/update/sport")
    public ResponseResult UpdateSport(@CurrentUserId String userId, @RequestParam(value = "sport") Integer sport ,@RequestParam(value = "step") Integer step) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        System.out.println(time);
        if(userServcie.findSportByIdDate(userId,time) != null) {    //更新运动消耗和运动步数
            userServcie.updateSport(userId,sport,step,time);
        }
        else {
            userServcie.InsertSport(userId,sport,step,time);
        }
        return new ResponseResult(200,"操作成功！");
    }

    @PostMapping("/get_information")
    public ResponseResult Get_Information(@CurrentUserId String userId){
        ArrayList<Map> res = new ArrayList<>();
        //本人信息
        User user = userServcie.findUserbyId(userId);
        Float height = user.getUsr_height();
        Float weight = user.getNew_weight();
        Long age = user.getUsr_age();
        Float BMI = weight/((height/100)*(height/100));    //  体重(kg) / 身高^2 （米）
        Float base = 0.0F;   //基础代谢
        if(user.getUsr_sex()) {
            base = (float) (66 + 13.7 * weight + 5 * height - 6.8 * age);
        }
        else {
            base = (float) (655 + 9.6 * weight + 1.7 * height - 4.7 * age);
        }
        Map<String,Object> temp = new HashMap<>();
        temp.put("height",height);
        temp.put("weight",weight);
        temp.put("age",age);
        temp.put("BMI",BMI);
        temp.put("base",base);
        res.add(temp);
        //家庭成员信息
        List<UsrFamily> families = userServcie.GetFamily(userId);
        for(UsrFamily family : families) {
            if(Objects.equals(family.getUsr_id1(), userId)) {
                user = userServcie.findUserbyId(family.getUsr_id2());
            }
            else {
                user = userServcie.findUserbyId(family.getUsr_id1());
            }
            height = user.getUsr_height();
            weight = user.getNew_weight();
            age = user.getUsr_age();
            BMI = weight/((height/100)*(height/100));    //  体重(kg) / 身高^2 （米）
            if(user.getUsr_sex()) {
                base = (float) (66 + 13.7 * weight + 5 * height - 6.8 * age);
            }
            else {
                base = (float) (655 + 9.6 * weight + 1.7 * height - 4.7 * age);
            }
            Map<String,Object> temp2 = new HashMap<>();
            temp2.put("height",height);
            temp2.put("weight",weight);
            temp2.put("age",age);
            temp2.put("BMI",BMI);
            temp2.put("base",base);
            res.add(temp2);
        }
        return new ResponseResult(200,res);
    }



}
