package com.example.diet.Mapper;

import com.example.diet.Domain.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();

    public List<User> GetUser(String user_phone);  //看看是否已经注册
    public long InsertUser(User user);  //插入一个用户
    public Integer RecordWater(int userId);    //获取喝水量
    public String GetDayWater(int userId);  //获取喝水表的日期
    public void SetZero(int userId);  //将喝水量置零
    public void UpdateDayWater(int userId,String today);  //更新喝水表的日期为今天
    public void UpdateWater(int userId,long water_num);   //更新喝水量



    public List<UsrFamily> GetFamily(int userId);   //获取家庭成员
    public List<Recent_diet> GetBreakDiet(int userId, String date);  //获取今日早餐饮食记录
    public List<Recent_diet> GetLunchDiet(int userId, String date);  //获取今日午餐饮食记录
    public List<Recent_diet> GetDinnerDiet(int userId, String date);  //获取今日晚餐饮食记录


    public List<WeightRecord> GetWeight(int userId);



    @MapKey("user_id")
    User findUserbyPhonenum(String userphone, String password);

    @MapKey("user_id")
    User findUserbyPhonenum1(String usrphone);

    void InsertRegisterInfo(RegisterInfo registerInfo);

    User findById(String userId);

    void updateInfo(User user);

    WeightRecord findUserbyDateId(String userId, String time);

    void InsertWeight(String userId, Float weight, String time);

    void UpdateWeight(String userId, Float weight, String time);


    public long GetRecipeId(String rec_name);  //获取食谱id

    public void InsertDiet(Recent_diet recentDiet);  //插入饮食记录



    long getUserId(String usrPhone);

    void InsertWater(long userId, int i,String date);

    void InsertCal(long userId, int i,String date);

    void InsertFamilyRelation(UsrFamily usrFamily);

    List<String> findAllergenById(String usr_id);

    List<String> findDiseaseById(String usr_id);

    User findUserById(String usr_id);

    Integer getSexByUserId(String usr_id);

    List<Integer> findTasteIdById(String usr_id);

    List<Integer> findDiseaseIdById(String usr_id);

    Integer findSportByIdDate(String userId, String date);

    void updateSport(String userId, Integer sport, Integer step, String date);

    void InsertSport(String userId, Integer sport, Integer step, String date);
}
