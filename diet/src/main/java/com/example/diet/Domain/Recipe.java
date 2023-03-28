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
    private String recCal;
    /**
     * 菜品制作难度
     */
    private String recDiff;
    /**
     * 菜品辅料
     */
    private String recExcipient;
    /**
     * 脂肪
     */
    private String recFat;
    /**
     * 菜谱id
     */
    private long recid;
    /**
     * 建议少吃人群
     */
    private String recLess;
    /**
     * 菜品主料
     */
    private String recMain;
    /**
     * 建议多吃人群
     */
    private String recMore;
    /**
     * 菜谱名
     */
    private String recName;
    /**
     * 建议不吃人群
     */
    private String recNo;
    /**
     * 菜品做法
     */
    private String recPrac;
    /**
     * 制作步骤
     */
    private String recStep;
    /**
     * 糖量
     */
    private String recSugar;
    /**
     * 菜品口味
     */
    private String recTaste;
    /**
     * 菜品制作时间
     */
    private String recTime;
    /**
     * 菜谱图片url
     */
    private String recurl;
}
