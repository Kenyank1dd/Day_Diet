package com.example.daydiet.model.entity

data class Post_User(
    val postList : List<Post>,
    val usrList : List<User>
)


// 获取一个Integer信息
data class PostResponse(var data:Post_User ): BaseResponse() {}