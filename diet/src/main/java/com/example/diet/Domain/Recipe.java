package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    /**
     * 菜谱id
     */
    private long recid;
    /**
     * 菜谱名
     */
    private String recName;
    /**
     * 菜谱图片url
     */
    private String recurl;
}
