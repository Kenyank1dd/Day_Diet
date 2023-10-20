package com.example.daydiet.model.entity

data class RecipeEntity(
    val rec_id : Long = 0,
    val rec_name : String = "",
    var rec_url : String = "",    //菜品图片的url
    val rec_prac : String = "",    //菜品做法
    val rec_taste : String = "",  //菜品口味
    val rec_time : String = "",   //菜品制作时间
    val rec_diff : String = "",   //菜品制作难度
    val rec_main : String = "",    //菜品主料
    val rec_excipient : String = "",    //菜品辅料
    var rec_step : String = "",  //菜品步骤
    val rec_less : String = "",   //菜品建议少吃人群
    val rec_more : String = "",   //菜品建议多吃人群
    val rec_no : String = "",     //菜品建议不吃人群
    val rec_sugar : String = "",    //菜品含糖量
    val rec_cal : String = "",  //菜品热量
    val rec_fat : String = "",   //菜品脂肪含量
    val type_url : String = "",
    val recipe_url : String = "",
    val type_desc : String = "",
    val recipe_desc : String = ""
    // url
    // string
    // url
    // string
)

// 获取推荐菜谱信息
data class RecipeResponse(var data:List<RecipeEntity> ): BaseResponse() {}

