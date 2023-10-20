package com.example.daydiet.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.example.daydiet.viewmodel.UserViewModel

val LocalUserViewModel = compositionLocalOf<UserViewModel> {
    error("User View Model Context Not Found")
}