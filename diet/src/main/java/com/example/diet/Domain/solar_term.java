package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class solar_term {
    //节气表
    private long st_id; //节气的id
    private String st_name; //节气名称
    private DateTime  st_startdate; //节气开始时间
    private DateTime  st_enddate;  //节气结束时间
}
