package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private long rec_id;    //菜谱id
    private String rec_name;  //菜谱名称
    private String rec_url;   //菜谱图片url
}
