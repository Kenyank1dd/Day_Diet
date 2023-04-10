package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfo {
    private String usr_id;
    private String usr_phone;
    private String usr_password;
    private String usr_name;
    private boolean usr_sex;
    private String usr_height;
    private String usr_weight;
    private Long usr_age;
    private String tar_weight;
    private String tar_time;
}
