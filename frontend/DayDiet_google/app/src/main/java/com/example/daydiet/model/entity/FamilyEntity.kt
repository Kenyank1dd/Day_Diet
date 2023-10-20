package com.example.daydiet.model.entity

data class FamilyEntity(
    var allergens : List<String>,
    var disease : List<String>,
    var relation : String,
    var height : Float,
    var weight : Float,
    var age : Long,
    var BMI : Float,
    var base : Float   //基础代谢
)