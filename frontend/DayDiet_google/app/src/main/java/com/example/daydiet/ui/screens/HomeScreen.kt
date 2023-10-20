package com.example.daydiet.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.camera.CameraActivity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.DetectResultViewModel
import com.example.daydiet.viewmodel.SolarTermViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    drviewmodel: DetectResultViewModel,
    navHostController: NavHostController,
    solarTermViewModel: SolarTermViewModel = viewModel()
) {
    var solarterm_index = 0

    solarTermViewModel.solar_term_list.forEachIndexed { index, solarTermEntity ->
        val now_date = LocalDate.now()
        val date_format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val parsed_date = LocalDate.parse(solarTermEntity.date, date_format)
        if(now_date.isAfter(parsed_date)){
            solarterm_index = index
        }
    }

    val context = LocalContext.current
    val userViewModel = LocalUserViewModel.current

    var cameraBack : ArrayList<String>? = null

    val target = Intent(context, CameraActivity::class.java)

    // 食材扫描yolov5
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("ddddresultcode", it.resultCode.toString())
        drviewmodel.list.clear()
        if(it.resultCode == 1){
            it.data?.apply {
                cameraBack = getStringArrayListExtra("food-result")
                if(cameraBack != null){
                    cameraBack!!.forEach{
                        Log.d("ddddreceive", it)
                        drviewmodel.list.add(it)
                    }
                }
                else{
                    Log.d("dddd", "not receive")
                }
                navHostController.navigate("DetectResultScreen")
            }
        }
        else{
            it.data?.getStringExtra("food-result")?.let { it1 ->
                Log.d("ddddcodezero", it1)
            }
        }
    }

//    if (!userViewModel.logged) {
//        navHostController.navigate(Destinations.LoginScreen.route)
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            // Day Diet标志
            item {
                Image(
                    painter = painterResource(id = R.drawable.daydietimage),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(75.dp)
                        .padding(top = 20.dp, bottom = 10.dp),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }

            // 搜索框
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                        .clip(RoundedCornerShape(10.dp))
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
                            "搜索食谱",
                            color = Color(0xFF808080),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis// 超出以省略号显示
                        )
                    }
                }
            }

            // 五个功能选项
            item {
                Row(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp, top = 4.dp, bottom = 4.dp)
                        .height(250.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(8f)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(
                                    onClick = {
                                        navHostController.navigate(Destinations.Rank.route)
                                    },
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.foodrankicon_g),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "食谱排行", fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(8f)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(
                                    onClick = {
                                        navHostController.navigate(Destinations.RobotScreen.route)
                                    },
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.robot_g),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "健康咨询", fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1.3f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(8f)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(
                                    onClick = {
                                        if (userViewModel.logged) {
                                            launcher.launch(target)
                                        } else {
                                            navHostController.navigate(Destinations.LoginScreen.route)
                                        }
                                    },
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cameraicon_g),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "扫描食材", fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(8f)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(
                                    onClick = {
                                        navHostController.navigate(Destinations.PlateScreen.route)
                                    },
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.plateicon_g),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "创意摆盘", fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(8f)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(
                                    onClick = {
                                        navHostController.navigate(Destinations.DarkScreen.route)
                                    }
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.darkdieticon_g),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "黑暗料理", fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            // 季节时令
            item {
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                    elevation = 10.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                ) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
//                            painter = painterResource(id = R.drawable.chunfen),
                            painter = painterResource(id = solarTermViewModel.solar_term_list[solarterm_index].picture),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = solarTermViewModel.solar_term_list[solarterm_index].name,
                                color = Color(0xFF333333),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Text(
                                text = solarTermViewModel.solar_term_list[solarterm_index].date_interval,
                                color = Color(0xFF606060),
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(
                                                start = 8.dp,
                                                end = 2.dp,
                                                top = 4.dp,
                                                bottom = 3.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0x3900DC64))
                                            .weight(1f)
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = solarTermViewModel.solar_term_list[solarterm_index].tips_1,
                                            fontSize = 16.sp,
                                            color = Color(0xFF444444),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(
                                                start = 8.dp,
                                                end = 2.dp,
                                                top = 3.dp,
                                                bottom = 8.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0x394EB3FF))
                                            .weight(1f)
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = solarTermViewModel.solar_term_list[solarterm_index].tips_2,
                                            fontSize = 16.sp,
                                            color = Color(0xFF444444),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(
                                                start = 2.dp,
                                                end = 8.dp,
                                                top = 4.dp,
                                                bottom = 3.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0x39FCE081))
                                            .weight(1f)
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = solarTermViewModel.solar_term_list[solarterm_index].tips_3,
                                            fontSize = 16.sp,
                                            color = Color(0xFF444444),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(
                                                start = 2.dp,
                                                end = 8.dp,
                                                top = 3.dp,
                                                bottom = 8.dp
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0x39FA632F))
                                            .weight(1f)
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = solarTermViewModel.solar_term_list[solarterm_index].tips_4,
                                            fontSize = 16.sp,
                                            color = Color(0xFF444444),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
