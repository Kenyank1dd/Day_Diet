package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usr_body {
    //用户身体表
    private long bd_id;  //运动id
    private long usr_id; //用户id
    private DateTime bd_date; //运动日期
    private long step_num;  //步数
    private long cal_num;  //消耗的卡路里
    private long sport_time;   //运动时长
    private long usr_wt;  //用户体重

}
