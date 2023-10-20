package com.example.daydiet.ui.secondscreens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.navigation.Destinations
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun SettingScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val userViewModel = LocalUserViewModel.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "设置",
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
            item {
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0xFF33F894)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
//                                        .padding(top = 15.dp, bottom = 8.dp)
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .clickable { navHostController.navigate(Destinations.AboutDayDietScreen.route) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.about),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(45.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        text = "关于益食", fontSize = 18.sp,
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
                                    .clickable {
                                        if(userViewModel.logged){
                                            navHostController.navigate(Destinations.PersonalInfoScreen.route)
                                        }
                                        else{
                                            navHostController.navigate(Destinations.LoginScreen.route)
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.change),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(45.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        text = "修改信息", fontSize = 18.sp,
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
                                    .clickable {
                                        if(userViewModel.logged){
                                            navHostController.navigate(Destinations.RecommendSetting.route)
                                        }
                                        else{
                                            navHostController.navigate(Destinations.LoginScreen.route)
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.setting_recommend),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(45.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        text = "推荐设置", fontSize = 18.sp,
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
        }
    }
}