package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usr_dis {
    //用户疾病表
    private long usr_id;  //用户id
    private List<Long> dis_id;  //所有疾病id
}
