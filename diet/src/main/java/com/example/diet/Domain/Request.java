package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private Integer req_id;
    private String from_usr_id;
    private String to_usr_id;    //一开始是phone   需要从phone改成usr_id
    private String req_msg;    //关系
}
