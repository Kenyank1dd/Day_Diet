package com.example.daydiet.model.entity

data class Diet_Record(
    var rd_id : Long,
    var rd_usr : Long,
    var rec_name : String,
    var g_num : Int,
    var cal_num : Int,
    var rd_time : String,
    var rd_type : Long
)