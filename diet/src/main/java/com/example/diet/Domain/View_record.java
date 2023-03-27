package com.example.diet.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class View_record {
    //浏览记录表
    private long view_id;  //浏览记录id
    private long view_usr;  //浏览用户id
    private long view_rec;  //浏览食谱id
    private String view_time;  //浏览时间
}
