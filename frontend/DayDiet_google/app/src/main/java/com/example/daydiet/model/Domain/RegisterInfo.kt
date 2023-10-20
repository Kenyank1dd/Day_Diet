package com.example.daydiet.model.entity

data class RegisterInfo(
    val usr_id : Long,
    val usr_phone : String,
    val usr_password : String,
    val usr_name : String,
    val usr_sex : Boolean,
    val usr_height : String,
    val usr_weight : String,
    val usr_age : Long,
    val tar_weight : String,
    val tar_time : String
)

// 获取一个RegisterInfo
data class InfoResponse(var data:RegisterInfo ): BaseResponse() {}
