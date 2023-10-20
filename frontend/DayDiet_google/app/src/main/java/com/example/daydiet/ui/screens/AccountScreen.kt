package com.example.daydiet.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.components.TopAppBarr
import com.example.daydiet.ui.navigation.Destinations
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountScreen(
    pagerState: PagerState,
    navHostController: NavHostController,
//    onNavigateToLogin: ()->Unit
) {

    val scope= rememberCoroutineScope()

    val userViewModel = LocalUserViewModel.current

//    var username = "未登录"
    var username by remember {
        mutableStateOf("未登录")
    }
    var logbuttontext by remember {
        mutableStateOf("登录账号")
    }
    var userlevel by remember {
        mutableStateOf(" ? ")
    }
    var buttoncolor by remember {
        mutableStateOf(Color(0xFF4EB3FF))
    }

    if(userViewModel.logged){
        if(userViewModel.userInfo != null){
            username = userViewModel.userInfo!!.usr_name
            userlevel = "6"
        }
        logbuttontext = "退出登录"
        buttoncolor = Color(0x77E78618)
    }

    Column {
        if(userViewModel.net_ok == false){
            userViewModel.setnet_ok(true)
            Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
        }
        if(userViewModel.accounterror == true){
            userViewModel.setaccounterror(false)
            Toast.makeText(LocalContext.current, "账号或密码错误", Toast.LENGTH_SHORT).show()
        }
        if(userViewModel.loginacc == false) {
            userViewModel.setloginacc(true)
            Toast.makeText(LocalContext.current, "登录成功", Toast.LENGTH_SHORT).show()
        }
        TopAppBarr {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.settingicon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navHostController.navigate(Destinations.SettingScreen.route)
                    }
            )
            Spacer(modifier = Modifier.width(25.dp))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {

                // 个人
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
//                        .height(140.dp)
                    ) {
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
//                            .weight(7f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.userpicture),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 30.dp)
                                    .clip(CircleShape)
                                    .size(80.dp)
                                    .border(1.dp, Color(0x77A86AD7), CircleShape)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Column() {
                                Text(
                                    text = username,
                                    color = Color(0xFF333333),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0x9900C864))
                                ) {
                                    Text(
                                        text = "等级" + userlevel + ">",
                                        fontSize = 13.sp,
                                        color = Color(0xFF333333),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(
                            modifier = Modifier
                                .padding(start = 25.dp, end = 25.dp)
                                .fillMaxWidth()
//                                .weight(3f)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(text = "6",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(text = "动态",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(text = "21",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(text = "粉丝",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(text = "3",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(text = "关注",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
                // 基本信息
                item {
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF33F894)),
                        elevation = 5.dp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "基本信息",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                            )
                            Row(modifier = Modifier.fillMaxSize()) {
                                
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.White),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clickable {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.DietGoalScreen.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.goalicon),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "饮食目标", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
//                                    Row(
//                                        modifier = Modifier
//                                            .weight(1f)
//                                            .fillMaxWidth(),
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.Center
//                                    ) {
//                                        Image(
//                                            painter = painterResource(id = R.drawable.healthicon),
//                                            contentDescription = null,
//                                            modifier = Modifier.size(50.dp),
//                                            contentScale = ContentScale.Crop
//                                        )
//                                        Spacer(modifier = Modifier.size(8.dp))
//                                        Text(
//                                            text = "健康方案", fontSize = 18.sp,
//                                            color = Color.Black,
//                                            fontWeight = FontWeight.Bold
//                                        )
//                                    }
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clickable {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.MyFavorite.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.myloveicon),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "我的收藏", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.White),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch(Dispatchers.Main) {
                                                    pagerState.scrollToPage(1)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.statisticicon),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "统计数据", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .clickable {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.MyHistory.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.historyicon),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = "浏览历史", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                // 更多服务
                item {
                    Spacer(modifier = Modifier.size(10.dp))
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF33F894)),
                        elevation = 5.dp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp)
                            .fillMaxWidth()
                            .height(190.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                        ){
                            Text(
                                text = "更多服务",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Row(
                                    modifier = Modifier
//                                        .padding(top = 15.dp, bottom = 8.dp)
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .clickable { navHostController.navigate(Destinations.CallUsScreen.route) },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.more_phone),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(45.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "联系我们", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color(0xFF777777),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 30.dp)
                                    )
                                }

                                Divider(modifier = Modifier.padding(start = 25.dp, end = 25.dp))

                                Row(
                                    modifier = Modifier
//                                        .padding(top = 8.dp, bottom = 15.dp)
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .clickable { navHostController.navigate(Destinations.SettingScreen.route) },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.more_setting),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(45.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "设置", fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color(0xFF777777),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 30.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                // 退出登录
                item {
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(buttoncolor)
                                .clickable {
                                    if(userViewModel.logged){
                                        userViewModel.islogin = false
                                        Log.d("+++666","成功退出登录")
                                        userViewModel.exitlogin()
                                        username = "未登录"
                                        logbuttontext = "登录账号"
                                        userlevel = " ? "
                                        buttoncolor = Color(0xFF4EB3FF)
                                    }
                                    else{
                                        navHostController.navigate(Destinations.LoginScreen.route)
                                    }
                                }
                        ) {
                            Text(
                                text = logbuttontext,
                                fontSize = 22.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
                // 联系我们&退出登录
//                item {
//                    Row() {
//                        Row(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(8.dp))
//                                .background(Color(0xFF4EB3FF))
//                        ) {
//                            Text(
//                                text = "联系我们",
//                                fontSize = 22.sp,
//                                color = Color.White,
//                                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp)
//                            )
//                        }
//                        Spacer(modifier = Modifier.size(35.dp))
//                        Row(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(8.dp))
//                                .background(Color(0x77E78618))
//                        ) {
//                            Text(
//                                text = "退出登录",
//                                fontSize = 22.sp,
//                                color = Color.White,
//                                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp)
//                            )
//                        }
//                    }
//                }
                // 设备列表
//                item {
//                    Surface(
//                        shape = RoundedCornerShape(15.dp),
//                        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
//                        elevation = 10.dp,
//                        color = Color.White,
//                        modifier = Modifier
//                            .padding(start = 25.dp, end = 25.dp)
//                            .fillMaxWidth()
//                            .height(130.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .background(Color.White)
//                                .fillMaxSize()
//                        ){
//                            Text(
//                                text = "我的设备",
//                                color = Color(0xFF333333),
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier.padding(start = 15.dp, top = 8.dp)
//                            )
//
//                            Row(
//                                modifier = Modifier.fillMaxSize(),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.watchicon),
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(40.dp),
//                                    contentScale = ContentScale.Crop
//                                )
//                                Spacer(modifier = Modifier.size(8.dp))
//                                Text(
//                                    text = "OPPO WATCH SE", fontSize = 18.sp,
//                                    color = Color.Black,
//                                    fontWeight = FontWeight.Bold
//                                )
//                                Spacer(modifier = Modifier.size(16.dp))
//                                Row(
//                                    modifier = Modifier
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .background(Color(0xFF00C864)),
//                                    horizontalArrangement = Arrangement.Center
//                                ) {
//                                    Text(
//                                        text = "已连接",
//                                        fontSize = 15.sp,
//                                        color = Color.White,
//                                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp)
//                                    )
//                                }
//                            }
//                        }
//
//                    }
//                }
            }
        )
    }
}
