package com.example.daydiet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarr( content: @Composable () -> Unit ) {

    // 标题栏高度
    val appBarHeight = 56.dp

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(appBarHeight),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBarr(){
        Text(text = "标题")
    }
}