package com.example.daydiet.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class DetectResultViewModel: ViewModel() {
    var list = mutableStateListOf<String>()
        private set

    var jsonresponse = mutableStateOf("")
        private set
}