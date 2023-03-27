package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disease {
    // 疾病表
    private long dis_id;   //疾病id
    private String dis_name;   //疾病名称

}
