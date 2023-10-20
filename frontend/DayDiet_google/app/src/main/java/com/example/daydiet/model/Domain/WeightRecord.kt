package com.example.daydiet.model.Domain

import com.example.daydiet.model.entity.BaseResponse

data class WeightRecord (
    val usr_id : Long,
    val usr_weight : Float,
    val date : String
)
// 获取用户的体重记录
data class WeightResponse(var data:List<WeightRecord> ): BaseResponse() {}