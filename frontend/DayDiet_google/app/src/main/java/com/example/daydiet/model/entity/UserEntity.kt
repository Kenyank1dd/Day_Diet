package com.example.daydiet.model.entity

import androidx.compose.runtime.mutableStateListOf

data class UserEntity(
    var name: String,
    var level: Int,
    var gender: String,
    var height: Int,
    var newweight: Float,
    var age: Int,
    var dietGoal: String,
    var weightStart: Float,
    var weightGoal: Float,
    var dateStart: String,
    var periodGoal: Int
)