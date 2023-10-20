package com.example.daydiet.ui.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daydiet.model.entity.Diet_Record

@Composable
fun RecentEat(
    diet:Diet_Record
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        var widthdp = LocalConfiguration.current.screenWidthDp
        widthdp = (widthdp - 60) / 3
        Column(
            modifier = Modifier
                .width(widthdp.dp)
                .height(68.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0x3322E023)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = diet.rec_name,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
        Column(
            modifier = Modifier
                .width((widthdp + 20).dp)
                .height(68.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0x3322E023)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(diet.cal_num.toString())
                        withStyle(
                            SpanStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666)
                            )
                        ){
                            append("千卡/100克")
                        }
                    },
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0x3322E023)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(diet.g_num.toString())
                        withStyle(
                            SpanStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666)
                            )
                        ){
                            append("克")
                        }
                    },
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            modifier = Modifier
                .width((widthdp - 20).dp)
                .height(68.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0x3322E023)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (diet.cal_num * diet.g_num / 100).toString(),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = "千卡",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(3.dp)
            )
        }
    }
}