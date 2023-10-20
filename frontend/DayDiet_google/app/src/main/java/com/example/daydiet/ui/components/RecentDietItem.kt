package com.example.daydiet.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.RecentDietEntity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.ui.screens.CircleRing
import kotlin.math.roundToInt

@Composable
fun RecentDietItem(
    recentdiet: RecentDietEntity,
    base: Int,
    navHostController: NavHostController
) {
    // 圆环进度条高度
    var boxWidthDp: Int
    with(LocalConfiguration.current){
        boxWidthDp = (screenWidthDp / 2.5).roundToInt()
    }

    Surface(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
        elevation = 10.dp,
        color = Color.White,
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "饮食记录", fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
            ){
                // 进度条
                Column(modifier = Modifier.fillMaxHeight()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 15.dp)
                            .size(boxWidthDp.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircleRing(
                            boxWidthDp,
                            recentdiet.cal_num * 1f / (base + recentdiet.sport),
                            recentdiet.step * 1f / 8000,
                            recentdiet.sport * 1f / 300
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.weight(1f))

                Column{
                    Spacer(modifier = Modifier.size(8.dp))
                    Column() {
                        Row(){
                            Image(
                                painter = painterResource(id = R.drawable.process_kcal),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(
                                text = "饮食热量",
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                append(recentdiet.cal_num.toString())
                                withStyle(
                                    SpanStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF666666)
                                    )
                                ){
                                    append(" / " + (base + recentdiet.sport).toString() + "千卡")
                                }
                            },
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column() {
                        Row(){
                            Image(
                                painter = painterResource(id = R.drawable.process_active),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(
                                text = "运动热量",
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                append(recentdiet.sport.toString())
                                withStyle(
                                    SpanStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF666666)
                                    )
                                ){
                                    append(" / " + 300 + "千卡")
                                }
                            },
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column() {
                        Row(){
                            Image(
                                painter = painterResource(id = R.drawable.process_steps),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(
                                text = "今日步数",
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                append(recentdiet.step.toString())
                                withStyle(
                                    SpanStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF666666)
                                    )
                                ){
                                    append(" / 8千步")
                                }
                            },
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

    Surface(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.5.dp, Color(0x55E2E023)),
        elevation = 5.dp,
        color = Color.White,
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.noodles),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = recentdiet.date, fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp))
            Text(
                text = "早餐",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if(recentdiet.breakfast?.isEmpty() == true){
                    Text(
                        text = "暂无数据",
                        fontSize = 17.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                else{
                    recentdiet.breakfast?.forEach{
                        RecentEat(it)
                    }
                }
            }
            Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp))
            Text(
                text = "午餐",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if(recentdiet.lunch?.isEmpty() == true){
                    Text(
                        text = "暂无数据",
                        fontSize = 17.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                else{
                    recentdiet.lunch?.forEach{
                        RecentEat(it)
                    }
                }
            }
            Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp))
            Text(
                text = "晚餐",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if(recentdiet.dinner?.isEmpty() == true){
                    Text(
                        text = "暂无数据",
                        fontSize = 17.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                else{
                    recentdiet.dinner?.forEach{
                        RecentEat(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CircleRing(
    boxWidthDp: Int,
    process_kcal: Float,
    process_steps: Float,
    process_active: Float
) {
    Canvas(
        modifier = Modifier
            .size(boxWidthDp.dp)
    ){
        // 热量记录圆环
        var angle_kcal = 0f
        if(process_kcal > 1.0f){
            angle_kcal = 270f
        }
        else{
            angle_kcal = (process_kcal * 270)
        }

        drawArc(
            Color(0x3300DC64),
            startAngle = -225f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )

        drawArc(
            Color(0xFF00DC64),
            startAngle = -225f,
            sweepAngle = angle_kcal,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )
    }

    Canvas(modifier = Modifier.size((boxWidthDp - 70).dp)){
        // 步数记录圆环
        var angle_steps = 0f
        if(process_steps > 1.0f){
            angle_steps = 270f
        }
        else{
            angle_steps = (process_steps * 270)
        }

        drawArc(
            Color(0x334EB3FF),
            startAngle = -225f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )

        drawArc(
            Color(0xFF4EB3FF),
            startAngle = -225f,
            sweepAngle = angle_steps,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )
    }

    Canvas(modifier = Modifier.size((boxWidthDp - 35).dp)){
        // 活动热量记录圆环
        var angle_active = 0f
        if(process_active > 1.0f){
            angle_active = 270f
        }
        else{
            angle_active = (process_active * 270)
        }

        drawArc(
            Color(0x33F78618),
            startAngle = -225f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )

        drawArc(
            Color(0xFFF78618),
            startAngle = -225f,
            sweepAngle = angle_active,
            useCenter = false,
            style = Stroke(50f, cap = StrokeCap.Round)
        )
    }
}