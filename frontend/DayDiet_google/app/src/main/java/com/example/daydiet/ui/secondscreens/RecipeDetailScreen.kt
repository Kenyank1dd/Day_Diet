package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.daydiet.R
import com.example.daydiet.service.StringUtil
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun RecipeDetialScreen(
    recipeid: Int,
    recipe: RecipeViewModel,
    navHostController: NavController,
    onBack: ()->Unit
) {
    recipe.findDetail(recipeid)

//    recipe.initial_recipe()

    var steps_list = mutableStateListOf<String>()
    var stringUtil = StringUtil()
    steps_list.clear()

    if(recipe.recipedetail.value.rec_step.isNotEmpty()){
        Log.d("+++length", stringUtil.getNum(recipe.recipedetail.value.rec_step).toString())
        var len = stringUtil.getNum(recipe.recipedetail.value.rec_step)
        for (j in 0 until len) {
            Log.d("++++++", stringUtil.getStep(recipe.recipedetail.value.rec_step, j))
            steps_list.add(stringUtil.getStep(recipe.recipedetail.value.rec_step, j))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "菜谱详情",
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(bottom = 15.dp, end = 8.dp),
                text = { Text(text = "文化溯源") },
                icon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "文化")
                },
                onClick = {
                    navHostController.navigate("${Destinations.RecipeCultureScreen.route}/${recipeid}")
                },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55EEEEEE)),
            horizontalAlignment = Alignment.Start,
            content = {
                item {
                    AsyncImage(
                        model = recipe.recipedetail.value.rec_url, contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(4 / 3f)
                    )
                }

                // 菜名
                item {
                    Text(
                        text = recipe.recipedetail.value.rec_name,
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 3.dp)
                    )
                }

                // 难度 口味 时间 制作方式
                item {
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = recipe.recipedetail.value.rec_diff, fontSize = 14.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pot),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = recipe.recipedetail.value.rec_prac, fontSize = 14.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bottle),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = recipe.recipedetail.value.rec_taste, fontSize = 14.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.timer),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = recipe.recipedetail.value.rec_time, fontSize = 14.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
//                    Divider()
                }

                // 营养素含量
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Surface(
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                            elevation = 1.dp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxSize()
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 30.dp)
//                                        .height(180.dp)
                                        .background(Color.White),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    SingleBar(name = "糖", value = recipe.recipedetail.value.rec_sugar.substring(0, recipe.recipedetail.value.rec_sugar.length-1).toDouble(), max = 30.0)
                                    Spacer(modifier = Modifier.width(15.dp))
                                    SingleBar(name = "热量", value = recipe.recipedetail.value.rec_cal.substring(0, recipe.recipedetail.value.rec_cal.length-1).toDouble(), max = 400.0)
                                    Spacer(modifier = Modifier.width(15.dp))
                                    SingleBar(name = "脂肪", value = recipe.recipedetail.value.rec_fat.substring(0, recipe.recipedetail.value.rec_fat.length-1).toDouble(), max = 40.0)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(){
                                    Text(
                                        text = "○适宜人群",
                                        color = Color(0xFF555555),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(15.dp),
                                        color = Color(0xFF00C864),
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 8.dp)
                                    ) {
                                        Text(
                                            text = "建议多吃",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.recipedetail.value.rec_more,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(){
                                    Text(
                                        text = "○适量人群",
                                        color = Color(0xFF555555),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(15.dp),
                                        color = Color(0xFFFFCA21),
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 8.dp)
                                    ) {
                                        Text(
                                            text = "控制少吃",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.recipedetail.value.rec_less,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(){
                                    Text(
                                        text = "○禁忌人群",
                                        color = Color(0xFF555555),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(15.dp),
                                        color = Color.Red,
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 8.dp)
                                    ) {
                                        Text(
                                            text = "不建议吃",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.recipedetail.value.rec_no,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF0F608),
                            modifier = Modifier
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "营养分析",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 步骤
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Surface(
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                            elevation = 1.dp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxSize()
                            ){
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "○主料",
                                    color = Color(0xFF555555),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.recipedetail.value.rec_main,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "○辅料",
                                    color = Color(0xFF555555),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.recipedetail.value.rec_excipient,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF0F608),
                            modifier = Modifier
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "食材",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                itemsIndexed(steps_list){ index, step_string ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Surface(
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                            elevation = 1.dp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxSize()
                            ){
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = step_string,
                                    color = Color(0xFF777777),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 30.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF0F608),
                            modifier = Modifier
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "步骤" + (index + 1).toString(),
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        )
    }
}

@Composable
fun FloatingButton_toCulture() {
//    Column(Modifier.padding(10.dp).background(Color.Yellow)) {
//
//    }
    FloatingActionButton(
        onClick = { },
    ) {
        Text(text = "悬浮按钮")
    }
    Spacer(modifier = Modifier.height(30.dp))
    ExtendedFloatingActionButton(
        text = {
            Text(text = "悬浮按钮")
        },
        icon = {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "喜爱")
        },
        onClick = {

        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),

    )
}

@Composable
fun SingleBar(
    name: String,
    value: Double,
    max: Double
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(
                    if (name == "糖") {
                        Color(0xFF03DAC5)
                    } else if (name == "热量") {
                        Color(0xFFBB86FC)
                    } else {
                        Color(0x77E78618)
                    }
                )
                .width(40.dp)
                .height(
                    (180 * value / max).dp
                )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = name,
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
    }
}