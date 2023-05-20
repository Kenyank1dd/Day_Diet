package com.example.diet.Service;

public interface RecordService {


    public Integer RecordCal(String userId);

    public Integer UpdateCal(String userId,long cal_num);

    public void updateAllergens(String userId,String allergen);
    public void updateDisease(String userId,String diseases);

    public Long getCal(String userId,String date);
    public Long getStep(String userId,String date);
}
