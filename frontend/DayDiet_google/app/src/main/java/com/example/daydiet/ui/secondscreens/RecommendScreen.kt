package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.Category
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.model.entity.User
import com.example.daydiet.ui.components.PyqItem
import com.example.daydiet.ui.components.RadarBean
import com.example.daydiet.ui.components.RadarChart
import com.example.daydiet.ui.components.RecipeItem
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.DetectResultViewModel
import com.example.daydiet.viewmodel.FoodViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.example.daydiet.viewmodel.SearchViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

@Composable
fun RecommendScreen(
    navHostController: NavHostController,
    onBack: ()->Unit,
    recipeViewModel: RecipeViewModel,
    drviewmodel: DetectResultViewModel = viewModel()
) {

    val categories by mutableStateOf(
        listOf(
            Category("第一组"),
            Category("第二组"),
            Category("第三组")
        )
    )
//    var categoryIndex by mutableStateOf(0)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "推荐结果",
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

        if(recipeViewModel.net_bad.value == true) {
            recipeViewModel.net_bad.value = false
            Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0x6643FFD5)
                        )
                    )
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.recommended),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(60.dp)
                    .padding(top = 10.dp, bottom = 1.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            // 分类标签
            TabRow(
                selectedTabIndex = recipeViewModel.list_index.value,
                backgroundColor = Color(0xFFFFFFFF),
                contentColor = Color(0xFF00C864),
                indicator = @Composable { tabPositions ->

                    val currentTabPosition = tabPositions[recipeViewModel.list_index.value]
                    // 修改指示器长度
                    val currentTabWidth by animateDpAsState(
                        targetValue = currentTabPosition.width / 3,
                        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                    )
                    // 修改指示器偏移量为居中
                    val indicatorOffset by animateDpAsState(
                        targetValue = currentTabPosition.left + (currentTabPosition.width / 2 - currentTabWidth / 2),
                        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                    )
                    // 修改Modifier
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.BottomStart)
                            .offset(x = indicatorOffset)
                            .width(currentTabWidth)
                            .height(3.5.dp)//修改指示器高度为1dp，默认2dp
                    )
                }
            ) {
                categories.forEachIndexed { index, category->
                    Tab(
                        selected = recipeViewModel.list_index.value == index,
                        onClick = {
                            recipeViewModel.list_index.value = index
                        },
                        selectedContentColor = Color(0xFF000000),
                        unselectedContentColor = Color(0xFF808080)
                    ) {
                        Text(
                            text = category.title,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Divider(modifier = Modifier.height(0.5.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(recipeViewModel.list_index.value == 0){
                    // 推荐食谱列表
                    items(recipeViewModel.list){ recipe->
                        Log.d("+++666", recipe.rec_url)
                        RecipeItem(
                            recipe,
                            navHostController = navHostController
                        )
                    }
                }
                if(recipeViewModel.list_index.value == 1){
                    // 推荐食谱列表
                    items(recipeViewModel.list1){ recipe->
                        Log.d("+++666", recipe.rec_url)
                        RecipeItem(
                            recipe,
                            navHostController = navHostController
                        )
                    }
                }
                if(recipeViewModel.list_index.value == 2){
                    // 推荐食谱列表
                    items(recipeViewModel.list2){ recipe->
                        Log.d("+++666", recipe.rec_url)
                        RecipeItem(
                            recipe,
                            navHostController = navHostController
                        )
                    }
                }

                item{
                    if(recipeViewModel.list_index.value == 0){
                        if(recipeViewModel.score_list.isEmpty()){

                        }
                        else{
                            val list = listOf(
                                RadarBean("食材契合", recipeViewModel.score_list.get(0)),
                                RadarBean("营养结构", recipeViewModel.score_list.get(1)),
                                RadarBean("特殊需求", recipeViewModel.score_list.get(2)),
                                RadarBean("季节时令", recipeViewModel.score_list.get(3)),
                                RadarBean("饮食偏好", recipeViewModel.score_list.get(4))
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "分析报告",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    RadarChart(
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                            .size(180.dp),
                                        list
                                    )
                                }
                            }
                        }
                    }

                    if(recipeViewModel.list_index.value == 1){
                        if(recipeViewModel.score_list1.isEmpty()){

                        }
                        else{
                            val list = listOf(
                                RadarBean("食材契合", recipeViewModel.score_list1.get(0)),
                                RadarBean("营养结构", recipeViewModel.score_list1.get(1)),
                                RadarBean("特殊需求", recipeViewModel.score_list1.get(2)),
                                RadarBean("季节时令", recipeViewModel.score_list1.get(3)),
                                RadarBean("饮食偏好", recipeViewModel.score_list1.get(4))
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "分析报告",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    RadarChart(
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                            .size(180.dp),
                                        list
                                    )
                                }
                            }
                        }
                    }

                    if(recipeViewModel.list_index.value == 2){
                        if(recipeViewModel.score_list2.isEmpty()){

                        }
                        else{
                            val list = listOf(
                                RadarBean("食材契合", recipeViewModel.score_list2.get(0)),
                                RadarBean("营养结构", recipeViewModel.score_list2.get(1)),
                                RadarBean("特殊需求", recipeViewModel.score_list2.get(2)),
                                RadarBean("季节时令", recipeViewModel.score_list2.get(3)),
                                RadarBean("饮食偏好", recipeViewModel.score_list2.get(4))
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "分析报告",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    RadarChart(
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp)
                                            .size(180.dp),
                                        list
                                    )
                                }
                            }
                        }
                    }
                }

                item{
                    if(recipeViewModel.list_index.value == 0){
                        if(recipeViewModel.reason.isEmpty()){

                        }
                        else{
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "推荐原因",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    recipeViewModel.reason.forEachIndexed{ index, it->
                                        Text(
                                            text = (index + 1).toString() + "、" + it + "。",
                                            fontSize = 17.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 20.dp, top = 3.dp, bottom = 3.dp, end = 20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if(recipeViewModel.list_index.value == 1){
                        if(recipeViewModel.reason1.isEmpty()){

                        }
                        else{
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "推荐原因",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    recipeViewModel.reason1.forEachIndexed{ index, it->
                                        Text(
                                            text = (index + 1).toString() + "、" + it + "。",
                                            fontSize = 17.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 20.dp, top = 3.dp, bottom = 3.dp, end = 20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if(recipeViewModel.list_index.value == 2){
                        if(recipeViewModel.reason2.isEmpty()){

                        }
                        else{
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = "推荐原因",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 10.dp, top = 8.dp, bottom = 20.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    recipeViewModel.reason2.forEachIndexed{ index, it->
                                        Text(
                                            text = (index + 1).toString() + "、" + it + "。",
                                            fontSize = 17.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 20.dp, top = 3.dp, bottom = 3.dp, end = 20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}