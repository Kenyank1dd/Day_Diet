package com.example.diet.Mapper;

import com.example.diet.Domain.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();

    public List<User> GetUser(String user_phone);  //看看是否已经注册
    public void InsertUser(User user);  //插入一个用户
    public Integer RecordWater(int userId);    //获取喝水量
    public String GetDayWater(int userId);  //获取喝水表的日期
    public void SetZero(int userId);  //将喝水量置零
    public void UpdateDayWater(int userId,String today);  //更新喝水表的日期为今天
    public void UpdateWater(int userId,long water_num);   //更新喝水量

    public Integer RecordCal(int userId);    //获取卡路里摄入量
    public String GetDayCal(int userId);  //获取喝水表的日期
    public void SetZero2(int userId);  //将卡路里摄入量置零
    public void UpdateDayCal(int userId,String today);  //更新卡路里摄入表的日期为今天
    public void UpdateCal(int userId,long cal_num);   //更新卡路里摄入量

    public List<UsrFamily> GetFamily(int userId);   //获取家庭成员
    public List<Recent_diet> GetBreakDiet(int userId);  //获取今日早餐饮食记录
    public List<Recent_diet> GetLunchDiet(int userId);  //获取今日午餐饮食记录
    public List<Recent_diet> GetDinnerDiet(int userId);  //获取今日晚餐饮食记录
    public Recipe GetRecipe(long recipe_id);  //获取菜谱的详细信息

//    public DietRecord RecordDiet(String userId, String date);
    public List<WeightRecord> GetWeight(int userId);

//    public Post_User GetPost(int userId);
    public List<User> GetInterestUser(int userId);   //获取关注用户信息
    public List<Post> GetInterestPost(int userId);   //获取关注用户动态

//    public List<Article> GetArticle(int userId);  //可以设置为静态
    public List<Article> GetCollectArticle(int userId);
    public int GetArticleId(String article_title);  //获取文章id 理论上来说有点点问题  因为id是主键 title不是主键
    public void InsertCollectArticle(int userId,int articleId);  //插入收藏文章记录



    @MapKey("family_id")
    List<Map<String,Object>> findFamilyMessagebyId(Integer userId);
    @MapKey("family_id")
    List<Map<String,Object>> findFamilyAllergenbyId(Integer userId);

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

    List<FamilyInfo> findFamilyByusrId(String usrId);

    public List<FamilyInfo> Get_dis_all(int userId);

    public List<FamilyInfo> GetFamilyInfo(int userId, String relation);

    public void InsertFamily(FamilyInfo familyInfo);

    public void UpdateFamily(FamilyInfo familyInfo);

    public long GetRecipeId(String rec_name);  //获取食谱id

    public void InsertDiet(Recent_diet recentDiet);  //插入饮食记录

    void UpdateCal(String usrId, String day, String cal_num);

    @MapKey("usr_id")
    Map getCal(String usrId);

    void UpdateDayCal(String usrId, String day, String cal_num);

    long getUserId(String usrPhone);

    void InsertWater(long userId, int i,String date);

    void InsertCal(long userId, int i,String date);

    void InsertFamilyRelation(UsrFamily usrFamily);

    List<String> findAllergenById(String usr_id);

    List<String> findDiseaseById(String usr_id);

    User findUserById(String usr_id);

    Integer getSexByUserId(String usr_id);

    List<Integer> findTasteIdById(String usr_id);

    List<Integer> findLabelIdById(String usr_id);

}
