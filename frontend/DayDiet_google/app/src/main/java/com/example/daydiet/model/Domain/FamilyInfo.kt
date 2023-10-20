package com.example.daydiet.model.Domain

import com.example.daydiet.model.entity.BaseResponse


data class FamilyInfo (
    val usr_id : Long,
    val family_id : Int,  //家庭成员id
    val relation : String,     //亲属关系
    val disease : String,   //慢性疾病list
    val allergen : String,   //过敏源list
    val sugar_need : Int,
    val cal_need :Int,
    val fat_need :Int
)

// 获取用户的家庭成员信息  过敏源和疾病
data class FamilyInfoResponse(var data:List<FamilyInfo> ): BaseResponse() {}

