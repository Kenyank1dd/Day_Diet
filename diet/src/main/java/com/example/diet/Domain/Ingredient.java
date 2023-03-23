package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    /**
     * 食材id
     */
    private long ingid;
    /**
     * 食材名
     */
    private String ingName;
    /**
     * 食材图片url
     */
    private String ingurl;
}
