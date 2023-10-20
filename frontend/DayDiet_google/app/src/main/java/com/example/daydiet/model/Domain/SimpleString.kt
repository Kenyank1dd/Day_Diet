package com.example.daydiet.model.entity

data class SimpleString(
    var s :List<String>
)

// 获取一个String信息
data class SimpleStringResponse(var data:List<String> ): BaseResponse() {}

