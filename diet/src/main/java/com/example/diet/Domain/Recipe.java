package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    /**
     * 热量
     */
    private String rec_cal;
    /**
     * 菜品制作难度
     */
    private String rec_diff;
    /**
     * 菜品辅料
     */
    private String rec_excipient;
    /**
     * 脂肪
     */
    private String rec_fat;
    /**
     * 菜谱id
     */
    private long rec_id;
    /**
     * 建议少吃人群
     */
    private String rec_less;
    /**
     * 菜品主料
     */
    private String rec_main;
    /**
     * 建议多吃人群
     */
    private String rec_more;
    /**
     * 菜谱名
     */
    private String rec_name;
    /**
     * 建议不吃人群
     */
    private String rec_no;
    /**
     * 菜品做法
     */
    private String rec_prac;
    /**
     * 制作步骤
     */
    private String rec_step;
    /**
     * 糖量
     */
    private String rec_sugar;
    /**
     * 菜品口味
     */
    private String rec_taste;
    /**
     * 菜品制作时间
     */
    private String rec_time;
    /**
     * 菜谱图片url
     */
    private String rec_url;

    private String type_url;

    private String recipe_url;

    private String type_desc;

    private String recipe_desc;
}
