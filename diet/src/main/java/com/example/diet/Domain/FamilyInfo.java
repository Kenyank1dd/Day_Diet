package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyInfo {
    private Long family_id;
    private Long usr_id;
    private String relation;
    private String disease;
    private String allergen;
    private Integer sugar_need;
    private Integer cal_need;
    private Integer fat_need;
}
