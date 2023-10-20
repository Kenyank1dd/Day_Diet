package com.example.daydiet.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.FamilyEntity

class FamilyViewModel:ViewModel() {

    // 专栏列表数据
    var list = mutableStateListOf<FamilyEntity>()
        private set
}