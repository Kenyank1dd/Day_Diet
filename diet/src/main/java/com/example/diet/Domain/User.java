package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 头像url
     */
    private String avatar_url;
    /**
     * 最后登录时间
     */
    private String last_login;
    /**
     * 注册时间
     */
    private String reg_time;
    /**
     * 用户年龄
     */
    private Long usr_age;
    /**
     * 用户邮箱
     */
    private String usr_email;
    /**
     * 用户id
     */
    private long usr_id;
    /**
     * 用户名
     */
    private String usr_name;
    /**
     * 用户密码
     */
    private String usr_password;
    /**
     * 用户手机号
     */
    private String usr_phone;
    /**
     * 用户性别
     */
    private Boolean usr_sex;
}
