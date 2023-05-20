package com.example.diet.Mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {

    Integer RecordCal(int userId, String time);    //获取卡路里摄入量
    void UpdateCal(int userId,long cal_num, String time);   //更新某个用户某天的卡路里摄入量
    List<Integer> findCalByIdDate(int userId, String time);   //通过用户id和日期查询卡路里摄入量


    // 更新过敏源和疾病
    void deleteAllergen(int userId);
    int findIdByAllergens(String allergen);
    void InsertAllergen(int userId, int allId);
    void deleteDisease(int userId);
    int findIdByDiseases(String disease);
    void InsertDisease(int userId, int disId);



    //获取某天的饮食热量和运动步数
    Long getCal(int userId, String date);
    Long getStep(int userId, String date);


}
