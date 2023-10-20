package com.example.daydiet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.FamilyEntity
import com.example.daydiet.model.entity.RequestEntity
import com.example.daydiet.model.entity.User
import com.example.daydiet.model.entity.UserEntity
import kotlin.math.roundToInt


// 专栏列表Item
@Composable
fun FamilyItem(family: FamilyEntity) {

    val userViewModel = LocalUserViewModel.current
    //TODO 获取请求列表

    Surface(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.5.dp, Color(0x55FA632F)),
        elevation = 5.dp,
        color = Color.White,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
    ) {

        var widthdp = LocalConfiguration.current.screenWidthDp
        widthdp = (widthdp - 64) / 3

        Column(
            modifier = Modifier
                .background(Color.White)
//                    .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.age),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    tint = Color(0xDDFA632F)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = family.relation, fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Column(
                    modifier = Modifier
                        .width(widthdp.dp)
//                        .height(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FA632F)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "身高", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                    Text(
                        text = family.height.toString() + "cm",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(2.dp))

                Column(
                    modifier = Modifier
                        .width(widthdp.dp)
//                        .height(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FA632F)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "最新体重", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                    Text(
                        text = family.weight.toString() + "kg",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(2.dp))

                Column(
                    modifier = Modifier
                        .width(widthdp.dp)
//                        .height(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FA632F)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "年龄", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                    Text(
                        text = family.age.toString() + "岁",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.size(2.dp))

            Row(modifier = Modifier.fillMaxWidth()){

                Spacer(modifier = Modifier.weight(0.5f))

                Column(
                    modifier = Modifier
                        .width(widthdp.dp)
//                        .height(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FA632F)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "最新BMI", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                    Text(
                        text = family.BMI.toString(),
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(2.dp))

                Column(
                    modifier = Modifier
                        .width(widthdp.dp)
//                        .height(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x33FA632F)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "基础代谢", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                    Text(
                        text = family.base.toInt().toString() + "千卡",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))
            }

            Divider(modifier = Modifier.padding(start = 60.dp, end = 60.dp, top = 8.dp, bottom = 8.dp))

            Text(
                text = "过敏源",
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                family.allergens.forEach {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Divider(modifier = Modifier.padding(start = 60.dp, end = 60.dp, top = 8.dp, bottom = 8.dp))

            Text(
                text = "疾病",
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                family.disease.forEach {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}