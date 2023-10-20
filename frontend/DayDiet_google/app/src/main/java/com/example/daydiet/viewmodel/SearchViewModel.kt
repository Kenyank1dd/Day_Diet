package com.example.daydiet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.Category

class SearchViewModel: ViewModel() {
    // 分类数据
    val categories by mutableStateOf(
        listOf(
            Category("食材"),
            Category("食谱")
        )
    )

    // 当前分类下标
    var categoryIndex by mutableStateOf(0)
        private set

    fun updateCategoryIndex(index: Int){
        categoryIndex =index
    }
}