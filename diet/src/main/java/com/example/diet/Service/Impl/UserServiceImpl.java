package com.example.diet.Service.Impl;

import com.example.diet.Aspect.InvokeLog;
import com.example.diet.Domain.*;
import com.example.diet.Mapper.UserMapper;
import com.example.diet.Service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Integer RecordCal(String userId){
        String date = userMapper.GetDayCal(Integer.parseInt(userId));
        long nowMillis = System.currentTimeMillis();
        DateTime now = new DateTime(nowMillis);
        String today = now.toString();
        System.out.println(today.substring(0,10));
        System.out.println(date.substring(0,10));
        if(!today.substring(0,10).equals(date.substring(0,10))){   //如果不相等要更新     如果日期相等 则直接获得
            userMapper.SetZero2(Integer.parseInt(userId));    //更新卡路里摄入量为0
            userMapper.UpdateDayCal(Integer.parseInt(userId),today.substring(0,10));  //更新为今天的日期
            return 0;   //直接返回0 即可
        }
        return userMapper.RecordCal(Integer.parseInt(userId));   //获取卡路里摄入量
    }

    @Override
    @InvokeLog
    public Integer UpdateCal(String userId,long cal_num){
        userMapper.UpdateCal(Integer.parseInt(userId),cal_num);
        return userMapper.RecordCal(Integer.parseInt(userId));
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
        List<Recipe> breakRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : breaklist){
            if(temp.getRd_time().substring(0,10).equals(date.substring(0,10))){
                breakRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }

        List<Recent_diet> lunchlist = userMapper.GetLunchDiet(Integer.parseInt(userId));   //取所有的该用户午餐记录
        List<Recipe> lunchRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : lunchlist){
            if(temp.getRd_time().substring(0,10).equals(date.substring(0,10))){
                lunchRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }

        List<Recent_diet> dinnerlist = userMapper.GetDinnerDiet(Integer.parseInt(userId));   //取所有的该用户晚餐记录
        List<Recipe> dinnerRecipe = new ArrayList<>();    //用于存储所有菜谱的详细信息
        for(Recent_diet temp : dinnerlist){
            if(temp.getRd_time().substring(0,10).equals(date.substring(0,10))){
                dinnerRecipe.add(userMapper.GetRecipe(temp.getRd_rec()));
            }
        }
        DietRecord result = new DietRecord(breakRecipe,lunchRecipe,dinnerRecipe);
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
    public Post_User GetPost(String userId){
        List<User> u = userMapper.GetInterestUser(Integer.parseInt(userId));
        List<Post> p = userMapper.GetInterestPost(Integer.parseInt(userId));
        Post_User pu = new Post_User(p,u);
        return pu;
    }

    @Override
    @InvokeLog
    public List<Article> GetArticle(String userId){   //获取推荐文章  可以设置为静态的

        return null;
    }

    @Override
    @InvokeLog
    public List<Article> GetCollectArticle(String userId){    //获取收藏文章
        return userMapper.GetCollectArticle(Integer.parseInt(userId));
    }

    @Override
    @InvokeLog
    public void CollectArticle(String userId,String article_title){
        int article_id = userMapper.GetArticleId(article_title);
        userMapper.InsertCollectArticle(Integer.parseInt(userId),article_id);
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

//    @Override
//    public void UpdateCal(String usrId, String day, String cal_num) {
//        Map map = userMapper.getCal(usrId);
//        long nowMillis = System.currentTimeMillis();
//        DateTime now = new DateTime(nowMillis);
//        String today = now.toString();
//        if (map.get("cal_date").equals(today.substring(0,10))) userMapper.UpdateCal(usrId,day,cal_num);
//        else userMapper.UpdateDayCal(usrId,day,cal_num);
//    }

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

}
