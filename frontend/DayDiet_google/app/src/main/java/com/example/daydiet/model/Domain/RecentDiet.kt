package com.example.daydiet.model.entity

data class RecentDiet (
    val breakList : List<RecipeEntity>,
    val lunchList : List<RecipeEntity>,
    val dinnerList : List<RecipeEntity>
)

// 获取用户的饮食记录信息
data class RecentDietResponse(var data:RecentDiet ): BaseResponse() {}
