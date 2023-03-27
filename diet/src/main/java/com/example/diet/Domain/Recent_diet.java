package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recent_diet {
    //今日饮食表
    private long rd_id;  //饮食id
    private long rd_usr; //用户id
    private long rd_rec;  //菜谱id
    private String rd_time; //饮食时间
    private  long rd_type; //所属餐时  早餐 午餐 晚餐
}
