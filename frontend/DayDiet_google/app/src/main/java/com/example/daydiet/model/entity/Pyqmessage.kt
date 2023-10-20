package com.example.daydiet.model.entity

data class Pyqmessage(
        val user: String,
        val msgcontent: String,
        var timestamp: String,
        val imageUrl: String
)

// 获取朋友圈信息
data class PyqmessageResponse(var data:List<Pyqmessage> ): BaseResponse() {}