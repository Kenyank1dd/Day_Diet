package com.example.diet.Mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {

    public Integer RecordCal(int userId, String time);    //获取卡路里摄入量
    public void UpdateCal(int userId,long cal_num, String time);   //更新某个用户某天的卡路里摄入量
    public List<Integer> findCalByIdDate(int userId, String time);   //通过用户id和日期查询卡路里摄入量

}
