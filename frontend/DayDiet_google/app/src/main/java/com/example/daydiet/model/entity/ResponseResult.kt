package com.example.daydiet.model.entity

import com.squareup.moshi.Json

class ResponseResult<T>(val code : Int,val message :String, val data : T){

}