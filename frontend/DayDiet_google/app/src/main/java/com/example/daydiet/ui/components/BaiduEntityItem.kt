package com.example.daydiet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.daydiet.model.entity.BaiduEntity

@Composable
fun BaiduEntityItem( baidu_entity: BaiduEntity ) {

    Column(modifier = Modifier
        .height(110.dp)
        .fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            AsyncImage(
//                model = baidu_entity.url, contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .width(160.dp)
//                    .aspectRatio(16 / 9f)
//                    .clip(RoundedCornerShape(8.dp))
//            )

            Column(modifier = Modifier
                .height(85.dp)
                .weight(1f)) {
                Text(
                    text = baidu_entity.name,
                    fontSize = 20.sp,
                    color = Color.Black,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.Bottom
                ){
                    Text(
                        text = buildAnnotatedString {
                            append(baidu_entity.calorie.toString())
                            withStyle(
                                SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF666666)
                                )
                            ){
                                append(" kcal/100g")
                            }
                        },
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .width(75.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF00E884)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "概率", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(3.dp))
                        Text(
                            text = (Math.round(baidu_entity.probability.toFloat()*10000)*0.01f).toString() + "%",
                            fontSize = 13.sp,
                            color = Color(0xFF000000),
                            modifier = Modifier.padding(3.dp),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 8.dp, bottom = 0.dp)
                .height(0.6.dp)
                .fillMaxWidth()
                .background(Color(0xFFDDDDDD))
        ){

        }
    }
}