package com.example.daydiet.model.entity

data class RecentDietEntity(
//    var date: String = "",
//    var breakfast: String = "",
//    var lunch: String = "",
//    var dinner: String = ""
    var date: String = "",
    var breakfast: List<Diet_Record>? = null,
    var lunch: List<Diet_Record>? = null,
    var dinner: List<Diet_Record>? = null,
    var cal_num : Int = 0,
    var sport : Int = 0,
    var step : Int = 0
)
