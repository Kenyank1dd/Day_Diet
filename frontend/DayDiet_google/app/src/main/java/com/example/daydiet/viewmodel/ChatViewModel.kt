package com.example.daydiet.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.BaiduEntity
import com.example.daydiet.model.entity.ChatEntity

class ChatViewModel: ViewModel() {
    var list = mutableStateListOf<ChatEntity>()
        private set

    var baidu_list = mutableStateListOf<BaiduEntity>()
        private set
}