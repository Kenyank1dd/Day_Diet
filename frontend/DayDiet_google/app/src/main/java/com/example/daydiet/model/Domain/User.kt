package com.example.daydiet.model.entity

data class User(
    var usr_id: Long = 0,
    var usr_name: String,
    var usr_phone: String,
    var usr_email: String = "",
    var usr_password: String,
    var usr_sex: Boolean,
    var usr_age: Int,
    var avatar_url: String = "",   //头像图片url
    var usr_height: Float = 0f,  //
    var usr_weight: Float = 0f,  //
    var new_weight: Float = 0f,
    var tar_weight: Float = 0f,  //目标体重
    var reg_time: String = "",
    var tar_time: Int = 0     //目标用时
// 注册时的usr_weight 最新的 new_weight 目标 tar_weight
)


// 获取推荐文章信息
data class UserResponse(var data:List<User> ): BaseResponse() {}

