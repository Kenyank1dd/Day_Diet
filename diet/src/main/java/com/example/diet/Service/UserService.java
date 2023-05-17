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

    public DietRecord RecordDiet(String userId, String date);
    public List<WeightRecord> GetWeight(String userId);




    List<Map<String,Object>> findFamilyMessagebyId(Integer userId);

    List<Map<String,Object>> findFamilyAllergenbyId(Integer userId);

    User findUserbyPhonenum(String userphone, String password);

    void InsertRegisterInfo(RegisterInfo registerInfo);

    User findById(String userId);

    void updateInfo(User user);

    WeightRecord  findUserbyDateId(String userId, String time);

    void InsertWeight(String userId, Float weight, String time);

    void UpdateWeight(String userId, Float weight, String time);

    List<FamilyInfo> findFamilyByusrId(String usrId);

    public List<FamilyInfo> Get_dis_all(String userId);


    public List<FamilyInfo> GetFamilyInfo(String userId, String relation);


    public void InsertFamily(FamilyInfo familyInfo);

    public void UpdateFamily(FamilyInfo familyInfo);


    public long GetRecipeId(String recName);

    public void InsertDiet(Recent_diet recentDiet);


    long getUserId(String usrPhone);

    void InsertWater(long userId, int i,String date);

    void InsertCal(long userId, int i,String date);

    void InsertFamilyRelation(Request request);

    List<String> findAllergenById(String usr_id);

    List<String> findDiseaseById(String usr_id);

    User findUserbyId(String usr_id);

    List<Map> findFamilyInfoByusrId(String userid);

    Integer findSportByIdDate(String userId, String time);

    void updateSport(String userId, Integer sport, Integer step, String date);

    void InsertSport(String userId, Integer sport, Integer step, String date);
}
