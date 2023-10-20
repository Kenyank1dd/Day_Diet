package com.example.daydiet.model.entity

data class SolarTermEntity(
    val name: String,
    val date: String,
    val date_interval: String,
    val picture: Int,
    val tips_1: String,
    val tips_2: String,
    val tips_3: String,
    var tips_4: String
)
