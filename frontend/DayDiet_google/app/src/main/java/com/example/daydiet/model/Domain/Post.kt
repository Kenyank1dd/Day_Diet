package com.example.daydiet.model.entity

data class Post (
    val post_id : Long,
    val author_id : Long,
    val post_title : String,
    val post_time : String,
    val post_type : Long,
    val pic_url : String,
    val vid_url : String,
    val post_like : Long,   //点赞
    val post_view : Long,   //浏览
    val post_comment : Long,  //评论
    val content : String   //内容
)