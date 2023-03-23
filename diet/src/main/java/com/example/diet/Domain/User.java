package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 头像url
     */
    private String avatarurl;
    /**
     * 最后登录时间
     */
    private DateTime lastLogin;
    /**
     * 注册时间
     */
    private DateTime regTime;
    /**
     * 用户年龄
     */
    private Long usrAge;
    /**
     * 用户邮箱
     */
    private String usrEmail;
    /**
     * 用户id
     */
    private long usrid;
    /**
     * 用户名
     */
    private String usrName;
    /**
     * 用户密码
     */
    private String usrPassword;
    /**
     * 用户手机号
     */
    private String usrPhone;
    /**
     * 用户性别
     */
    private Boolean usrSex;
}
