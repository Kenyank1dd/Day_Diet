package com.example.daydiet.ui.secondscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun AboutDayDietScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "关于益食",
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
    ){
        Surface(
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(0.5.dp, Color(0x55E2E023)),
            elevation = 5.dp,
            color = Color.White,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 8.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
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
                        text = "关于益食Day Diet", fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.applogo),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "版本号:",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "v1.0.0",
                            fontSize = 18.sp,
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}