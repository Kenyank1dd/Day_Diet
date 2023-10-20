package com.example.daydiet.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.daydiet.ui.components.WebView
import com.example.daydiet.ui.components.rememberWebViewState
import com.example.daydiet.viewmodel.ArticalViewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun ArticalDetailScreen(
    articalid: Int,
    articalViewModel: ArticalViewModel = viewModel(),
    onBack: ()->Unit
) {
    if(articalid >= 6){
        LaunchedEffect(Unit){
            articalViewModel.fetchInfo_swiper(articalid)
        }
    }
    else if(articalid < 0){
        LaunchedEffect(Unit){
            articalViewModel.fetchInfo_zhuanlandata(articalid + 3)
        }
    }
    else{
        LaunchedEffect(Unit){
            articalViewModel.fetchInfo(articalid)
        }
    }

    val webViewState = rememberWebViewState(data = articalViewModel.content)

//    val backPress = LocalOnBackPressedDispatcherOwner.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "文章详情",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                onBack()
                            }
                            .padding(8.dp)
                    )
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        ) {
            // 标题和简介
            WebView(state = webViewState)
        }
    }
}