package com.example.diet.Controller;

import com.example.diet.Domain.ResponseResult;
import com.example.diet.Resolver.CurrentUserId;
import com.example.diet.Service.RecordService;
import com.example.diet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}
