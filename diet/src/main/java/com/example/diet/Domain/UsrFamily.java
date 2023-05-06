package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrFamily {
    /**
     * 成员关系
     */
    private String relation1;

    private String relation2;
    /**
     * 对应用户id
     */
    private String usr_id1;

    private String usr_id2;
}
