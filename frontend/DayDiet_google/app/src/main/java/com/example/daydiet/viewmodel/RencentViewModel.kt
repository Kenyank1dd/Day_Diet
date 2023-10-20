package com.example.daydiet.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.RecentDietEntity

class RecentViewModel: ViewModel() {
    var list = mutableStateListOf<RecentDietEntity>()
        private set
}