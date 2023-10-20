package com.example.daydiet.model.entity

data class Family (
    val family_id : Int,
    val usr_id : Int,
    val relation : String,
    val sugar_need : String,
    val cal_need : String,
    val fat_need : String
//    val diseases : List<Int>,   //慢性疾病list
//    val allergens : List<String>    //过敏源list
)

// 获取用户的家庭成员信息
data class FamilyResponse(var data:List<Family> ): BaseResponse() {}

