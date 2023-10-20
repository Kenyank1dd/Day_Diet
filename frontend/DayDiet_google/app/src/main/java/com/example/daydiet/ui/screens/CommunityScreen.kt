package com.example.daydiet.ui.screens

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.ui.components.*
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.MainViewModel
import com.example.daydiet.viewmodel.PyqViewModel
import com.example.daydiet.viewmodel.ArticalViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CommunityScreen(
    vm: MainViewModel = viewModel(),
    pyqViewModel: PyqViewModel = viewModel(),
    articalViewModel: ArticalViewModel = viewModel(),
    recipeViewModel: RecipeViewModel = viewModel(),
    navHostController: NavHostController
) {

//    LaunchedEffect(Unit){
//        pyqViewModel.pyqmessageData()
//    }

    Column {

        // 标题栏
        TopAppBarr {

            Spacer(modifier = Modifier.width(20.dp))

            // 搜索按钮
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
                    .border(1.dp, Color(0xFFD5D5D5), RoundedCornerShape(10.dp)),
                color = Color(0xFFF5F5F5)
            ){
                Row(modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clickable(
                        onClick = { navHostController.navigate(Destinations.SearchDetail.route) },
                        // 去除点击效果
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color(0xFF00C864)
                    )
                    Text(
                        "搜索菜谱",
                        color = Color(0xFF808080),
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis// 超出以省略号显示
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color(0xFF00C864),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navHostController.navigate(Destinations.PersonalInfoScreen.route) }
            )

            Spacer(modifier = Modifier.width(20.dp))
        }

        // 分类标签
        TabRow(
            selectedTabIndex = vm.categoryIndex,
            backgroundColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF00C864),
            indicator = @Composable { tabPositions ->

                val currentTabPosition = tabPositions[vm.categoryIndex]
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
            vm.categories.forEachIndexed { index, category->
                Tab(
                    selected = vm.categoryIndex == index,
                    onClick = {
                        vm.updateCategoryIndex(index)
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){

            if(vm.categoryIndex == 0){
                // 朋友圈列表
                items(pyqViewModel.list){ pyq->
                    PyqItem(pyq)
                }
            }
            if(vm.categoryIndex == 1){
                // 轮播图
                item { SwiperContent(navHostController) }
                // 文章列表
                items(articalViewModel.list){ artical->
                    ArticalItem(
                        artical,
                        navHostController = navHostController
                    )
                }
            }
            if(vm.categoryIndex == 2){
                // 首图
                item {
                    Image(
                        painter = painterResource(id = R.drawable.zhuanlanpicture2),
//                    alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
                // 专栏列表
                item {
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        color = Color(0x15F02323),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Row(
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.tangniaobing),
                                    contentDescription = null,
                                    tint = Color(0xFFF02323),
                                    modifier = Modifier.size(35.dp)
                                )

                                Text(
                                    text = "高血糖专区",
                                    color = Color(0xAAF02323),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.gaoxuetangpicture),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 15.dp)
                                        .fillMaxHeight()
                                        .aspectRatio(16 / 9f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
//                                        modifier = Modifier.fillMaxSize(),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x88F02323))
                                        .clickable(
                                            onClick = {
                                                navHostController.navigate("${Destinations.ArticalDetail.route}/-3")
                                            },
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "高血糖饮食",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 2.dp)
                                    )
                                    Text(
                                        text = "注意事项  >",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 2.dp, bottom = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        color = Color(0x22138D13),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Row(
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.tongfeng),
                                    contentDescription = null,
                                    tint = Color(0xFF116D19),
                                    modifier = Modifier.size(35.dp)
                                )

                                Text(
                                    text = "痛风专区",
                                    color = Color(0xAA116D19),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.tongfengpicture),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 15.dp)
                                        .fillMaxHeight()
                                        .aspectRatio(16 / 9f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
//                                        modifier = Modifier.fillMaxSize(),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x88138D13))
                                        .clickable(
                                            onClick = {
                                                navHostController.navigate("${Destinations.ArticalDetail.route}/-2")
                                            },
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "痛风者饮食",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 2.dp)
                                    )
                                    Text(
                                        text = "注意事项  >",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 2.dp, bottom = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        color = Color(0x152323F0),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                            Row(
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.xinzangbing),
                                    contentDescription = null,
                                    tint = Color(0xFF687AD1),
                                    modifier = Modifier.size(35.dp)
                                )

                                Text(
                                    text = "心脏病专区",
                                    color = Color(0xFF687AD1),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.xinzangbingpicture),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 15.dp)
                                        .fillMaxHeight()
                                        .aspectRatio(16 / 9f)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
//                                        modifier = Modifier.fillMaxSize(),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x882323F0))
                                        .clickable(
                                            onClick = {
                                                navHostController.navigate("${Destinations.ArticalDetail.route}/-1")
                                            },
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "心脏病饮食",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 2.dp)
                                    )
                                    Text(
                                        text = "注意事项  >",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 2.dp, bottom = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(15.dp))
                }
            }
        }
    }
}
