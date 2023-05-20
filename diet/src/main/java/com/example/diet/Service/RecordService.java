package com.example.diet.Service;

import com.example.diet.Domain.Settings;

import java.util.List;

public interface RecordService {


    public Integer RecordCal(String userId);

    public Integer UpdateCal(String userId,long cal_num);

    public void updateAllergens(String userId,String allergen);
    public void updateDisease(String userId,String diseases);

    public Long getCal(String userId,String date);
    public Long getStep(String userId,String date);

    //修改  查找  插入推荐参数
    void UpdateSettings(String userId,Float[] settings);
    void InsertSettings(Long userId,Float[] settings);
    Settings getSettings(String userId);
}
