package com.example.daydiet.ui.secondscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.ui.components.*
import com.example.daydiet.viewmodel.ArticalViewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun MyFavoriteScreen(
    navHostController: NavHostController,
    articalViewModel:ArticalViewModel = viewModel(),
    onBack: ()->Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "我的收藏",
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
                            .clickable { onBack() }
                            .padding(8.dp)
                    )
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){

            items(articalViewModel.list){ artical->
                ArticalItem(
                    artical,
                    navHostController = navHostController
                )
            }

        }
    }
}