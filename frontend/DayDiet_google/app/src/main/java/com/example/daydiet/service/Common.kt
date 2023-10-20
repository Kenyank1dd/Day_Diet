package com.example.daydiet.service

object Common {

    const val Base_Url = "http://124.221.166.194:8080"
    const val Login = "/api/v1/login"  //登录
    const val Register = "/api/v1/register"  //注册
    const val Identify_recipe = "api/v1/identify/recipe"  //菜品识别
    const val Get_Pyq = "/api/v1/hello"
    const val Get_Category = "/api/v1/category"
    const val Get_Artical = "/api/v1/article"
    const val Recommend_Recipe = "/api/v1/recommend/recipe"   //菜谱推荐
    const val Search_Recipe = "/api/v1/search/recipe"      //搜索菜谱
    const val Rank_Recipe = "/api/v1/rank/recipe"     //食谱排行
    const val Generate_Picture = "/api/v1/generate/plating"  //生成摆盘
    const val Generate_Recipe = "/api/v1/generate/recipe"  //食谱生成
    const val Record_Water = "/api/v1/record/water"  //记录饮水量
    const val Update_Water = "/api/v1/update/water"  //更新喝水量
    const val Get_Family = "/api/v1/record/family"  //获取家庭成员信息
    const val Get_Diet = "/api/v1/record/recentdiet"  //获取饮食记录
    const val Get_Weight = "/api/v1/record/weight"  //获取今日体重
    const val Get_Community = "/api/v1/community/interest"  //获取关注用户动态
    const val Community_Article = "/api/v1/community/article"  //获取推荐文章
    const val Collect_Article = "api/v1/collect/article"  //获取收藏文章
    const val Add_Article = "api/v1/collect/article"   //增加用户收藏

    const val Update_RegisterInfo = "/api/v1/updateinfo"   //更改注册信息
    const val Get_dis_all = "/api/v1/get_dis_all"   //获取家庭用户的疾病和过敏源
    const val Update_dis_all = "/api/v1/update_dis_all"  //修改家庭成员的疾病和过敏源
    const val Update_Weight = "/api/v1/update/weight"   //修改体重记录
    const val Get_cal = "/api/v1/getcal"   //获取热量

}