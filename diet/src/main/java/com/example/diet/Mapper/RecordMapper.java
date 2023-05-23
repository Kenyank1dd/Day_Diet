package com.example.diet.Mapper;


import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.Settings;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RecordMapper {

    Integer RecordCal(int userId, String time);    //获取卡路里摄入量
    void UpdateCal(int userId,long cal_num, String time);   //更新某个用户某天的卡路里摄入量
    @MapKey("usr_id")
    Map<String,Object> findCalByIdDate(int userId, String time);   //通过用户id和日期查询卡路里摄入量


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

    //修改  查找  插入推荐参数
    void UpdateSettings(int userId,float settings0,float settings1,float settings2,float settings3,float settings4,float settings5);

    void InsertSettings(long userId,float settings0,float settings1,float settings2,float settings3,float settings4,float settings5);

    Settings getSettings(int userId);
}
