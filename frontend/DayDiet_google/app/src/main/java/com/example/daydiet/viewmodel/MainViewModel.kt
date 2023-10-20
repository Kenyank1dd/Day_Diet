package com.example.daydiet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.*
import com.example.daydiet.service.Homeservice
import retrofit2.http.Header

class MainViewModel:ViewModel() {

    // 分类数据
    val categories by mutableStateOf(
        listOf(
            Category("关注"),
            Category("推荐"),
            Category("专栏")
        )
    )

    // 当前分类下标
    var categoryIndex by mutableStateOf(1)
        private set

    fun updateCategoryIndex(index: Int){
        categoryIndex =index
    }
}



//val homeservice = Homeservice.instance()    //调用接口
//
//suspend fun register(info: RegisterInfo) : String {
//    val registerResponse = homeservice.register(info)
//    if(registerResponse.code == 200){   //注册成功
//        val message = registerResponse.message
//        return message
//    }else{ //不成功的情况
//        val message = registerResponse.message
//        return message
//    }
//}
//
//suspend fun login(user_phone :String,user_password :String){
//    val loginResponse = homeservice.login(user_phone,user_password)
//    if(loginResponse.code == 200){
//        list = loginResponse.data
//        print(loginResponse.data)
//    }else{//不成功的情况
//        val message = loginResponse.message
//    }
//}
//
//
//suspend fun recommendRecipe(ings :List<String>,token :String) :List<Recipe>{    //推荐菜谱
//    val recipeResponse = homeservice.Recommend_Recipe(ings, token)
//    if(recipeResponse.code == 200){
//        list = recipeResponse.data
//    }else{//不成功的情况
//        val message = recipeResponse.message
//    }
//    return recipeResponse.data
//}
//
//
//suspend fun identifyRecipe(diet_picture :String,token :String) :List<Recipe>{       //菜品识别
//    val recipeResponse = homeservice.IdentifyRecipe(diet_picture,token)
//    if(recipeResponse.code == 200){
//        list = recipeResponse.data
//    }else{//不成功的情况
//        val message = recipeResponse.message
//    }
//    return recipeResponse.data
//}
//
//
//suspend fun searchRecipe(rec_name :String) :Recipe{     //搜索菜谱
//    val recipeResponse = homeservice.Search_Recipe(rec_name)
//    if(recipeResponse.code == 200){
//        list = recipeResponse.data
//    }else{//不成功的情况
//        val message = recipeResponse.message
//    }
//    return recipeResponse.data.get(0)
//}
//
//
//suspend fun rankRecipe() :List<Recipe>{              //食谱排行
//    val recipeResponse = homeservice.Rank_Recipe()
//    if(recipeResponse.code == 200){
//        list = recipeResponse.data
//    }else{//不成功的情况
//        val message = recipeResponse.message
//    }
//    return recipeResponse.data
//}
//
//
//suspend fun generatePicture(rec_name: String) :String{   //生成摆盘照片
//    val pictureResponse = homeservice.Generate_Picture(rec_name)
//    if(pictureResponse.code == 200){
//        list = pictureResponse.data
//    }else{//不成功的情况
//        val message = pictureResponse.message
//    }
//    return pictureResponse.data.get(0)
//}
//
//
//suspend fun generateRecipe(ings_names :List<String>) :String{          //生成黑暗料理菜谱
//    val recipeResponse = homeservice.Generate_Recipe(ings_names)
//    if(recipeResponse.code == 200){
//        list = recipeResponse.data
//    }else{//不成功的情况
//        val message = recipeResponse.message
//    }
//    return recipeResponse.data.get(0)
//}
//
//suspend fun getWater(token :String) :Long{            //获取饮水量
//    val waterResponse = homeservice.Record_Water(token)
//    if(waterResponse.code == 200){
//        list = waterResponse.data
//    }else{//不成功的情况
//        val message = waterResponse.message
//    }
//    return waterResponse.data.get(0)
//}
//
//suspend fun updateWater(waternum :Int,token :String) :Long{            //更新饮水量
//    val waterResponse = homeservice.Update_Water(waternum,token)
//    if(waterResponse.code == 200){
//        list = waterResponse.data
//    }else{//不成功的情况
//        val message = waterResponse.message
//    }
//    return waterResponse.data.get(0)
//}
//
//suspend fun getFamily(token: String) :List<Family>{              //获取家庭成员信息
//    val familyResponse = homeservice.Get_Family(token)
//    if(familyResponse.code == 200){
//        list = familyResponse.data
//    }else{//不成功的情况
//        val message = familyResponse.message
//    }
//    return familyResponse.data
//}
//
//suspend fun getDiet(date :String,token :String) :RecentDiet{            //获取近日饮食记录
//    val dietResponse = homeservice.Get_Diet(date,token)
//    if(dietResponse.code == 200){
//        list = dietResponse.data
//    }else{//不成功的情况
//        val message = dietResponse.message
//    }
//    return dietResponse.data
//}
//
//
//suspend fun getWeight(token: String) :List<Long>{          //获取今日体重
//    val weightResponse = homeservice.Get_Weight(token)
//    if(weightResponse.code == 200){
//        list = weightResponse.data
//    }else{//不成功的情况
//        val message = weightResponse.message
//    }
//    return weightResponse.data
//}
//
//suspend fun getCommunity(token: String) :Post_User{       //获取关注用户动态
//    val communityResponse = homeservice.Get_Community(token)
//    if(communityResponse.code == 200){
//        list = communityResponse.data
//    }else{//不成功的情况
//        val message = communityResponse.message
//    }
//    return communityResponse.data
//}
//
//suspend fun communityArticle(token: String) :List<Article>{          //获取推荐文章
//    val articleResponse = homeservice.Community_Article(token)
//    if(articleResponse.code == 200){
//        list = articleResponse.data
//    }else{//不成功的情况
//        val message = articleResponse.message
//    }
//    return articleResponse.data
//}
//
//suspend fun collectArticle(token: String) :List<Article>{           //获取收藏文章
//    val articleResponse = homeservice.Collect_Article(token)
//    if(articleResponse.code == 200){
//        list = articleResponse.data
//    }else{//不成功的情况
//        val message = articleResponse.message
//    }
//    return articleResponse.data
//}
//
//
//suspend fun addArticle(article_title :String,token :String) :String{
//    val addResponse = homeservice.Add_Article(article_title,token)
//    if(addResponse.code == 200){
//        val message = addResponse.message
//        return message
//    }else{//不成功的情况
//        val message = addResponse.message
//        return message
//    }
//}