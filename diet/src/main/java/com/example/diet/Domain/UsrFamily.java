package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsrFamily {
    /**
     * 过敏源list
     */
    private String[] allergens;
    /**
     * 疾病list
     */
    private long[] diseases;
    /**
     * 成员id
     */
    private Long family_id;
    /**
     * 成员关系
     */
    private String relation;
    /**
     * 对应用户id
     */
    private long usr_id;

}
