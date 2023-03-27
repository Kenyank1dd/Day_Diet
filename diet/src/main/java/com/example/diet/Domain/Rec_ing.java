package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rec_ing {
    // 食谱食材关联表
    private long rec_id;   //食谱id
    private long ing_id;   //食材id
    private long ing_num;  //食材数量
}
