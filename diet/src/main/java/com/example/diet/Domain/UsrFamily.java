package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrFamily {


    /**
     * 成员id
     */
    private long family_id;
    /**
     * 成员关系
     */
    private String relation;
    /**
     * 对应用户id
     */
    private long usr_id;
    /**
     * 家庭成员所需糖类的消耗量
     */
    private long sugar_need;

    /**
     * 家庭成员所需热量的消耗量
     */
    private long cal_need;

    /**
     * 家庭成员所需脂肪的消耗量
     */
    private long fat_need;
}
