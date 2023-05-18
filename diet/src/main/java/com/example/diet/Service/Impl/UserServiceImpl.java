package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.*;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.min;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @InvokeLog
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @InvokeLog
    public boolean isRegister(String user_phone){
        List<User> users = userMapper.GetUser(user_phone);
        return !users.isEmpty();
    }

    @Override
    @InvokeLog
    public void InsertUser(User user){
        userMapper.InsertUser(user);
    }

    @Override
    @InvokeLog
    public Integer RecordWater(String userId){
        String date = userMapper.GetDayWater(Integer.parseInt(userId));
        long nowMillis = System.currentTimeMillis();
        DateTime now = new DateTime(nowMillis);
        String today = now.toString();
        System.out.println(today.substring(0,10));
        System.out.println(date.substring(0,10));
        if(!today.substring(0,10).equals(date.substring(0,10))){   //如果不相等要更新     如果日期相等 则直接获得
            userMapper.SetZero(Integer.parseInt(userId));    //更新喝水量为0
            userMapper.UpdateDayWater(Integer.parseInt(userId),today.substring(0,10));  //更新为今天的日期
            return 0;   //直接返回0 即可
        }
        return userMapper.RecordWater(Integer.parseInt(userId));   //获取喝水量
    }

    @Override
    @InvokeLog
    public Integer UpdateWater(String userId,long water_num){
        userMapper.UpdateWater(Integer.parseInt(userId),water_num);
        return userMapper.RecordWater(Integer.parseInt(userId));
    }



    @Override
    @InvokeLog
    public List<UsrFamily> GetFamily(String userId){
        return userMapper.GetFamily(Integer.parseInt(userId));
    }

    @Override
    @InvokeLog
    public DietRecord RecordDiet(String userId, String date){
        List<Recent_diet> breaklist = userMapper.GetBreakDiet(Integer.parseInt(userId));   //取所有的该用户早餐记录
        List<Recent_diet> lunchlist = userMapper.GetLunchDiet(Integer.parseInt(userId));   //取所有的该用户午餐记录
        List<Recent_diet> dinnerlist = userMapper.GetDinnerDiet(Integer.parseInt(userId));   //取所有的该用户晚餐记录
        DietRecord result = new DietRecord(breaklist,lunchlist,dinnerlist);
        return result;
    }

    @Override
    @InvokeLog
    public List<WeightRecord> GetWeight(String userId){
        List<WeightRecord> weights = userMapper.GetWeight(Integer.parseInt(userId));  //需要找到日期最近的七天
        List<WeightRecord> result = new ArrayList<>();     //从数据库取出的时候已经按照字典序排序了
        int len = weights.size();  //元素个数
        System.out.println(len);
        for(int i=0;i<min(7,len);i++){
            result.add(weights.get(len-i-1));
        }
        return result;
    }


    @Override
    @InvokeLog
    public List<Map<String,Object>> findFamilyMessagebyId(Integer userId) {
        return userMapper.findFamilyMessagebyId(userId);
    }

    @Override
    @InvokeLog
    public List<Map<String,Object>> findFamilyAllergenbyId(Integer userId) {
        return userMapper.findFamilyAllergenbyId(userId);
    }

    @Override
    @InvokeLog
    public User findUserbyPhonenum(String userphone, String password) {
        return userMapper.findUserbyPhonenum(userphone,password);
    }

    @Override
    @InvokeLog
    public void InsertRegisterInfo(RegisterInfo registerInfo) {
        userMapper.InsertRegisterInfo(registerInfo);
    }

    @Override
    @InvokeLog
    public User findById(String userId) {
        return userMapper.findById(userId);
    }

    @Override
    @InvokeLog
    public void updateInfo(User user) {
        userMapper.updateInfo(user);
    }

    @Override
    @InvokeLog
    public WeightRecord findUserbyDateId(String userId, String time) {
        return userMapper.findUserbyDateId(userId,time);
    }

    @Override
    @InvokeLog
    public void InsertWeight(String userId, Float weight, String time) {
        userMapper.InsertWeight(userId,weight,time);
    }

    @Override
    @InvokeLog
    public void UpdateWeight(String userId, Float weight, String time) {
        userMapper.UpdateWeight(userId,weight,time);
    }

    @Override
    public List<FamilyInfo> findFamilyByusrId(String usrId) {
        return userMapper.findFamilyByusrId(usrId);
    }

    @Override
    @InvokeLog
    public List<FamilyInfo> Get_dis_all(String userId){
        return userMapper.Get_dis_all(Integer.parseInt(userId));
    }

    @Override
    @InvokeLog
    public List<FamilyInfo> GetFamilyInfo(String userId, String relation){
        return userMapper.GetFamilyInfo(Integer.parseInt(userId),relation);
    }

    @Override
    @InvokeLog
    public void InsertFamily(FamilyInfo familyInfo){
        userMapper.InsertFamily(familyInfo);
    }

    @Override
    @InvokeLog
    public void UpdateFamily(FamilyInfo familyInfo){
        userMapper.UpdateFamily(familyInfo);
    }

    @Override
    @InvokeLog
    public long GetRecipeId(String rec_name){
        return userMapper.GetRecipeId(rec_name);
    }

    @Override
    @InvokeLog
    public void InsertDiet(Recent_diet recentDiet){
        userMapper.InsertDiet(recentDiet);
    }


    @Override
    @InvokeLog
    public long getUserId(String usrPhone){
        return userMapper.getUserId(usrPhone);
    }
    @Override
    @InvokeLog
    public void InsertWater(long userId, int i,String date){
        userMapper.InsertWater(userId,i,date);
    }

    @Override
    @InvokeLog
    public void InsertCal(long userId, int i,String date){ userMapper.InsertCal(userId,i,date); }

    @Override
    @InvokeLog
    public void InsertFamilyRelation(Request request) {
        UsrFamily usrFamily = new UsrFamily();
        usrFamily.setRelation1(request.getReq_msg());
        usrFamily.setUsr_id1(request.getFrom_usr_id());
        usrFamily.setUsr_id2(request.getTo_usr_id());
        Integer sex = userMapper.getSexByUserId(request.getTo_usr_id());
        switch (request.getReq_msg()) {
            case "父亲":
            case "母亲": {
                if(sex == 1) usrFamily.setRelation2("儿子");
                else usrFamily.setRelation2("女儿");
                break;
            }
            case "儿子":
            case "女儿": {
                if(sex == 1) usrFamily.setRelation2("父亲");
                else usrFamily.setRelation2("母亲");
                break;
            }
            case "妻子": {
                usrFamily.setRelation2("丈夫");
                break;
            }
            case "丈夫": {
                usrFamily.setRelation2("妻子");
                break;
            }
            case "奶奶":
            case "爷爷": {
                if(sex == 1) usrFamily.setRelation2("孙子");
                else usrFamily.setRelation2("孙女");
                break;
            }
            case "孙子":
            case "孙女": {
                if(sex == 1) usrFamily.setRelation2("爷爷");
                else usrFamily.setRelation2("奶奶");
                break;
            }
            case "外公":
            case "外婆": {
                if(sex == 1) usrFamily.setRelation2("外孙");
                else usrFamily.setRelation2("外孙女");
                break;
            }
            case "外孙":
            case "外孙女": {
                if(sex == 1) usrFamily.setRelation2("外公");
                else usrFamily.setRelation2("外婆");
                break;
            }
        }
        userMapper.InsertFamilyRelation(usrFamily);
    }

    @Override
    @InvokeLog
    public List<String> findAllergenById(String usr_id) {
        return userMapper.findAllergenById(usr_id);
    }

    @Override
    @InvokeLog
    public List<String> findDiseaseById(String usr_id) {
        return userMapper.findDiseaseById(usr_id);
    }

    @Override
    public User findUserbyId(String usr_id) {
        return userMapper.findUserById(usr_id);
    }

    @Override
    public List<Map> findFamilyInfoByusrId(String userid) {
        ArrayList<Map> res = new ArrayList<>();
        List<UsrFamily> families = GetFamily(userid);
        for(UsrFamily family : families) {
            Map<String,Object> temp = new HashMap<>();
            List<String> allergens;
            List<Integer> labels;
            List<Integer> tastes;
            ArrayList<Double> nutri_need = new ArrayList<>();
            User usr;
            double sugar,cal,fat;
            if(Objects.equals(family.getUsr_id1(), userid)) {
                allergens = findAllergenById(family.getUsr_id2());
                labels = findLabelIdById(family.getUsr_id2());
                tastes = findTasteIdById(family.getUsr_id2());
                usr = findUserbyId(family.getUsr_id2());
            }
            else {
                allergens = findAllergenById(family.getUsr_id1());
                labels = findLabelIdById(family.getUsr_id1());
                tastes = findTasteIdById(family.getUsr_id1());
                usr = findUserbyId(family.getUsr_id1());
            }
            temp.put("allergen",allergens);
            temp.put("label",labels);
            temp.put("taste",tastes);
            Float height = usr.getUsr_height();
            Float weight = usr.getNew_weight();
            if(usr.getUsr_sex()) {
                cal = 66 + 13.7 * weight + 5 * height - 6.8 * usr.getUsr_age();
            }
            else {
                cal = 655 + 9.6 * weight + 1.7 * height - 4.7 * usr.getUsr_age();
            }
            sugar = cal * 0.55 / 4;
            fat = cal * 0.25 / 9;
            nutri_need.add(sugar);
            nutri_need.add(cal);
            nutri_need.add(fat);
            temp.put("nutri",nutri_need);
            res.add(temp);
        }
        List<String> allergens = findAllergenById(userid);
        List<Integer> labels = findLabelIdById(userid);
        List<Integer> tastes = findTasteIdById(userid);
        ArrayList<Double> nutri_need = new ArrayList<>();
        double sugar,cal,fat;
        User usr = findUserbyId(userid);
        Float height = usr.getUsr_height();
        Float weight = usr.getNew_weight();
        if(usr.getUsr_sex()) {
            cal = 66 + 13.7 * weight + 5 * height - 6.8 * usr.getUsr_age();
        }
        else {
            cal = 655 + 9.6 * weight + 1.7 * height - 4.7 * usr.getUsr_age();
        }
        sugar = cal * 0.55 / 4;
        fat = cal * 0.25 / 9;
        nutri_need.add(sugar);
        nutri_need.add(cal);
        nutri_need.add(fat);
        Map<String,Object> temp = new HashMap<>();
        temp.put("allergen",allergens);
        temp.put("label",labels);
        temp.put("taste",tastes);
        temp.put("nutri",nutri_need);
        res.add(temp);
        return  res;
    }

    @Override
    public Integer findSportByIdDate(String userId, String time) {
        return userMapper.findSportByIdDate(userId,time);
    }

    @Override
    public void updateSport(String userId, Integer sport, Integer step, String date) {
        userMapper.updateSport(userId,sport,step,date);
    }

    @Override
    public void InsertSport(String userId, Integer sport, Integer step, String date) {
        userMapper.InsertSport(userId,sport,step,date);
    }

    private List<Integer> findTasteIdById(String usr_id) {
        return userMapper.findTasteIdById(usr_id);
    }

    private List<Integer> findLabelIdById(String usr_id) {
        return userMapper.findLabelIdById(usr_id);
    }

}
