package com.example.daydiet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.ArticalViewModel
import com.example.daydiet.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwiperContent(
    navHostController: NavHostController,
    swiperviewModel: ArticalViewModel = viewModel()
) {

    // 虚拟页数
    val virtualCount = Int.MAX_VALUE

    // 实际页数
    val actualCount = swiperviewModel.swiperData.size

    // 初始图片下标
    val initialIndex = virtualCount/2
    val pagerState = rememberPagerState(initialPage = initialIndex)

    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit){
        val timer = Timer()

        timer.schedule(object :TimerTask(){
            override fun run() {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

        },3000,3000)

        onDispose {
            timer.cancel()
        }
    }

    // 轮播图
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .background(color = Color.White)
    ) {

        HorizontalPager(
            count = virtualCount,
            state = pagerState,
             modifier = Modifier
                 .fillMaxSize()
                 .padding(2.dp)
                 .clip(RoundedCornerShape(8.dp))
        ) { index ->

            // index - (index.floorDiv(actualCount)) * actualCount
            // 需要对0特殊判断
            val actualIndex = (index - initialIndex).floorMod(actualCount)

            AsyncImage(
                model = swiperviewModel.swiperData[actualIndex].imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navHostController.navigate("${Destinations.ArticalDetail.route}/${swiperviewModel.swiperData[actualIndex].id}")
                    },
                    // .aspectRatio(7 / 3f)
                contentScale = ContentScale.Crop
            )
        }

        horizontalPagerIndicator(
            pagerState = pagerState,
            count = actualCount,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomEnd),
            indicatorWidth = 6.dp,
            activeColor = Color.White
        )
    }
}

private fun Int.floorMod(other: Int): Int = when (other){
    0 -> this
    else -> this - floorDiv(other) * other
}