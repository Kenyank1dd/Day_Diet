package com.example.daydiet.model.entity

data class Article(
    val article_id : Long,   //
    val content : String,    //文章html
    val pic_url : String,    //封面图片url
    val article_title : String,   //文章标题
    val article_src : String     //内容来源
)


// 获取推荐文章信息
data class ArticleResponse(var data:List<Article> ): BaseResponse() {}
