package com.example.daydiet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.FoodEntity

class FoodViewModel: ViewModel() {

    var list = listOf(
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        ),
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        ),
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        ),
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        ),
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        ),
        FoodEntity(
            "白菜",
            "14千卡/100克",
            "https://docs.bughub.icu/compose/assets/banner5.jpg"
        )
    )
        private set
}