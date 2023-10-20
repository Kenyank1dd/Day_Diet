package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.ResponseResult
import com.example.daydiet.model.entity.User
import com.example.daydiet.ui.components.*
import com.example.daydiet.viewmodel.*
import com.google.accompanist.insets.statusBarsPadding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun SearchResultScreen(
//    searchViewModel: SearchViewModel = viewModel(),
//    foodViewModel: FoodViewModel = viewModel(),
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    Log.d("++++666s-r", recipeViewModel.net_bad.value.toString())
    if(recipeViewModel.net_bad.value == true) {
        recipeViewModel.net_bad.value = false
        Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 标题栏
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    text = "搜索结果",
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
        Image(
            painter = painterResource(id = R.drawable.searchresults),
            alignment = Alignment.Center,
            modifier = Modifier
                .height(75.dp)
                .padding(top = 2.dp, bottom = 2.dp),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
        // 分类标签
//        TabRow(
//            selectedTabIndex = searchViewModel.categoryIndex,
//            backgroundColor = Color(0xFFFFFFFF),
//            contentColor = Color(0xFF00C864),
//            indicator = @Composable { tabPositions ->
//
//                val currentTabPosition = tabPositions[searchViewModel.categoryIndex]
//                // 修改指示器长度
//                val currentTabWidth by animateDpAsState(
//                    targetValue = currentTabPosition.width / 3,
//                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
//                )
//                // 修改指示器偏移量为居中
//                val indicatorOffset by animateDpAsState(
//                    targetValue = currentTabPosition.left + (currentTabPosition.width / 2 - currentTabWidth / 2),
//                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
//                )
//                // 修改Modifier
//                TabRowDefaults.Indicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentSize(Alignment.BottomStart)
//                        .offset(x = indicatorOffset)
//                        .width(currentTabWidth)
//                        .height(3.5.dp)//修改指示器高度为1dp，默认2dp
//                )
//            }
//        ) {
//            searchViewModel.categories.forEachIndexed { index, category->
//                Tab(
//                    selected = searchViewModel.categoryIndex == index,
//                    onClick = {
//                        searchViewModel.updateCategoryIndex(index)
//                    },
//                    selectedContentColor = Color(0xFF000000),
//                    unselectedContentColor = Color(0xFF808080)
//                ) {
//                    Text(
//                        text = category.title,
//                        modifier = Modifier.padding(vertical = 8.dp),
//                        fontSize = 18.sp
//                    )
//                }
//            }
//        }

        LazyColumn(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0x77FF9999)
                        )
                    )
                )
                .fillMaxSize()
        ){

//            if(searchViewModel.categoryIndex == 0){
//                // 食材列表
//                items(foodViewModel.list){ food->
//                    FoodItem(
//                        food,
//                        navHostController = navHostController
//                    )
//                }
//            }
            recipeViewModel.list.forEach {
                Log.d("++++666", it.rec_name)
            }
             // 食谱列表
            items(recipeViewModel.list){ recipe->
                RecipeItem(
                    recipe,
                    navHostController = navHostController
                )
            }
        }
    }
}