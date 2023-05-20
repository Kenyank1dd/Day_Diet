package com.example.diet.Service;

import com.example.diet.Domain.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> findAll();
    public boolean isRegister(String user_phone);
    public void InsertUser(User user);

    public Integer RecordWater(String userId);

    public Integer UpdateWater(String userId,long water_num);



    public List<UsrFamily> GetFamily(String userId);

    public List<String> findAllergenById(String usr_id);

    public List<String> findDiseaseById(String usr_id);





    public DietRecord RecordDiet(String userId, String date);


    User findUserbyPhonenum(String userphone, String password);

    void InsertRegisterInfo(RegisterInfo registerInfo);

    User findById(String userId);

    void updateInfo(User user);



    public List<WeightRecord> GetWeight(String userId);

    WeightRecord  findUserbyDateId(String userId, String time);

    void InsertWeight(String userId, Float weight, String time);

    void UpdateWeight(String userId, Float weight, String time);




    public long GetRecipeId(String recName);

    public void InsertDiet(Recent_diet recentDiet);


    long getUserId(String usrPhone);


    void InsertWater(long userId, int i,String date);

    void InsertCal(long userId, int i,String date);



    void InsertFamilyRelation(Request request);





    User findUserbyId(String usr_id);

    List<Map> findFamilyInfoByusrId(String userid);



    // 关于运动消耗和运动步数
    Integer findSportByIdDate(String userId, String time);

    void updateSport(String userId, Integer sport, Integer step, String date);

    void InsertSport(String userId, Integer sport, Integer step, String date);
}
