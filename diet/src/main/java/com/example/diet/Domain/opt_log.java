package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class opt_log {
    //日志表
    private long opt_id;    //操作id
    private String opt_time;  //操作时间
    private long opt_type;    //操作类型
    private long opt_usr;   //操作用户id
}
