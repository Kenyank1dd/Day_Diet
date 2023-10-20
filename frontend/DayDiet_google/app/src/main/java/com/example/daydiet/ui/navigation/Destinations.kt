package com.example.daydiet.ui.navigation

sealed class Destinations(val route: String){
    // 首页大框架
    object HomeFrame: Destinations("HomeFrame")

    // 登录界面
    object LoginScreen: Destinations("LoginScreen")
    // 注册界面
    object RegisterScreen: Destinations("RegisterScreen")
    // 专栏详情页
//    object ArticalDetail: Destinations("ArticalDetail")

    // 视频详情页
    object ArticalDetail: Destinations("ArticalDetial")

    // 我的收藏详情页
    object MyFavorite: Destinations("MyFavorite")

    // 饮食目标详情页
    object DietGoalScreen: Destinations("DietGoalScreen")

    // 个人信息编辑页
    object PersonalInfoScreen: Destinations("PersonalInfoScreen")

    // 关于DayDiet
    object AboutDayDietScreen: Destinations("AboutDayDietScreen")

    // 浏览历史详情页
    object MyHistory: Destinations("MyHistory")

    // 搜索详情页
    object SearchDetail: Destinations("SearchDetail")

    // 搜索结果页
    object SearchResult: Destinations("SearchResult")

    // 排行详情页
    object Rank: Destinations("Rank")

    // 相机目标检测
    object CameraObjDet: Destinations("CameraObjDet")

    //百度
    object BaiduResultScreen: Destinations("BaiduResultScreen")

    // 相机记录饮食
    object RecordCameraScreen: Destinations("RecordCameraScreen")

    // 目标检测结果显示
    //    object DetectResultScreen: Destinations("DetectResultScreen?dresult={dresult}")
    object DetectResultScreen: Destinations("DetectResultScreen")
    // 推荐结果详情页
    object RecommendScreen: Destinations("RecommendScreen")

    // 食谱详情页
    object RecipeDetailScreen: Destinations("RecipeDetailScreen")
    // 食谱文化溯源
    object RecipeCultureScreen: Destinations("RecipeCultureScreen")

    // 创意摆盘二级界面
    object PlateScreen: Destinations("PlateScreen")
    // 创意摆盘三级界面
    object PlateDetailScreen: Destinations("PlateDetailScreen")

    // 黑暗料理二级界面
    object DarkScreen: Destinations("DarkScreen")
    // 黑暗料理三级界面
    object DarkDetailScreen: Destinations("DarkDetailScreen")

    // 家庭成员详情页
    object FamilyScreen: Destinations("FamliyScreen")

    // 邀请家庭成员
    object InvestScreen: Destinations("InvestScreen")

    // 接收请求
    object RequestScreen: Destinations("RequestScreen")

    // 近日饮食详情页
    object RecentDietScreen: Destinations("RecentDietScreen")

    // 喝水记录详情页
    object DrinkScreen: Destinations("DrinkScreen")

    // 体重记录详情页
    object WeightScreen: Destinations("WeightScreen")

    // 联系我们详情页
    object CallUsScreen: Destinations("CallUsScreen")

    // 设置详情页
    object SettingScreen: Destinations("SettingScreen")

    // 推荐设置详情页
    object RecommendSetting: Destinations("RecommendSetting")

    // 对话机器人
    object RobotScreen: Destinations("RobotScreen")
}
