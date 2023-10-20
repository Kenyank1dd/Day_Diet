package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.UserViewModel
import java.time.Instant.now
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

@Composable
fun DietGoalScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val userViewModel = LocalUserViewModel.current

    Log.d("####", userViewModel.userInfo.toString())

    var BMI = userViewModel.userInfo!!.usr_weight /
            ( (userViewModel.userInfo!!.usr_height / 100f) * (userViewModel.userInfo!!.usr_height / 100f) )
    var BMIround = BMI.roundToInt()

    // 基础代谢
    // 男：66+（13.7*体重）+（5*身高）-（6.8*年龄）
    // 女：655+（9.6*体重）+（1.7*身高）-（4.7*年龄）
    var calbmi = 0f
    if(userViewModel.userInfo!!.usr_sex == true){
        calbmi = 66 + (13.7f * userViewModel.userInfo!!.new_weight) +
                (5 * userViewModel.userInfo!!.usr_height) - (6.8f * userViewModel.userInfo!!.usr_age)
    }
    else{
        calbmi = 655 + (9.6f * userViewModel.userInfo!!.new_weight) +
                (1.7f * userViewModel.userInfo!!.usr_height) - (4.7f * userViewModel.userInfo!!.usr_age)
    }
    var bmiround = calbmi.roundToInt()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White).fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .clickable { onBack() }
                                .padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.width(28.dp))
                        Text(
                            text = "饮食目标",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color(0xFF00C864),
                            modifier = Modifier
                                .size(25.dp)
                                .clickable { navHostController.navigate(Destinations.PersonalInfoScreen.route) }
                        )
                        Spacer(modifier = Modifier.width(18.dp))
                    }
                }
                Divider()
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            item{
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x55E2E023)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
//            .height(190.dp)
                ) {
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
                                tint = Color(0xFF12D043)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = "基本信息", fontSize = 18.sp,
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
                            var widthdp = LocalConfiguration.current.screenWidthDp
                            widthdp = (widthdp - 60) / 3
                            Column(
                                modifier = Modifier
                                    .width(widthdp.dp)
//                                    .height(85.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x3322E023)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "身高", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                Text(
                                    text = userViewModel.userInfo!!.usr_height.toString() + "cm",
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(widthdp.dp)
//                                    .height(85.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x3322E023)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "最新体重", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                Text(
                                    text = userViewModel.userInfo!!.new_weight.toString() + "kg",
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(widthdp.dp)
//                                    .height(85.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x3322E023)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "年龄", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                Text(
                                    text = userViewModel.userInfo!!.usr_age.toString() + "岁",
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Divider(modifier = Modifier.padding(start = 60.dp, end = 60.dp, top = 8.dp, bottom = 8.dp))
                        Text(
                            text = "最新BMI",
                            fontSize = 17.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = BMIround.toString(),
                                fontSize = 18.sp,
                                color = Color(0xFF666666),
                                textAlign = TextAlign.Center
                            )
                        }
                        Divider(modifier = Modifier.padding(start = 60.dp, end = 60.dp, top = 8.dp, bottom = 8.dp))
                        Text(
                            text = "代谢",
                            fontSize = 17.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = bmiround.toString() + "千卡",
                                fontSize = 18.sp,
                                color = Color(0xFF666666),
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }

            item{

                Spacer(modifier = Modifier.size(10.dp))

                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x225223FF)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
//            .height(190.dp)
                ) {
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
                                painter = painterResource(id = R.drawable.goal),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(CircleShape),
                                tint = Color(0xFF2283E0)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = "体重目标", fontSize = 18.sp,
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
                            var widthdp = LocalConfiguration.current.screenWidthDp
                            widthdp = (widthdp - 100) / 2
                            Column(
                                modifier = Modifier
                                    .width(widthdp.dp)
//                                    .height(85.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x222223F0)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "期望减重", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                var interval = userViewModel.userInfo!!.usr_weight -
                                        userViewModel.userInfo!!.tar_weight
                                var intervalround = (interval * 100f).roundToInt() / 100f
                                Text(
                                    text = intervalround.toString() + "kg",
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(widthdp.dp)
//                                    .height(85.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x222223F0)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "预期时间", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                                Text(
                                    text = userViewModel.userInfo!!.tar_time.toString() + "天",
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = "体重进度",
                            fontSize = 17.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )

                        progressbar(
                            value = (userViewModel.userInfo!!.new_weight - userViewModel.userInfo!!.usr_weight) /
                                ( userViewModel.userInfo!!.tar_weight - userViewModel.userInfo!!.usr_weight ),
                            Color(0x557273F0),
                            userViewModel.userInfo!!.usr_weight.toString() + "kg",
                            userViewModel.userInfo!!.tar_weight.toString() + "kg")

                        Text(
                            text = "时间进度",
                            fontSize = 17.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )

                        val datenow = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val parsedDate = LocalDate.parse(userViewModel.userInfo!!.reg_time, fmt)
                        val datestart = Date.from(parsedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                        val result = differentDays(datestart, datenow)
                        progressbar(
                            value = (result * 1.0f) / userViewModel.userInfo!!.tar_time,
                            Color(0x557273F0),
                            userViewModel.userInfo!!.reg_time,
                            userViewModel.userInfo!!.tar_time.toString() + "天")
                    }
                }
            }

            item{
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x37E02253)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
//            .height(190.dp)
                ) {
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
                                painter = painterResource(id = R.drawable.fire),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                tint = Color(0xFFE02253)
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = "热量缺口", fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        val mid = (userViewModel.userInfo!!.usr_weight - userViewModel.userInfo!!.tar_weight) * 3850 /
                                userViewModel.userInfo!!.tar_time
                        var calbmi2 = calbmi - mid
                        var bmiround2 = calbmi2.roundToInt()
                        Column(
                            modifier = Modifier
                                .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
                                .fillMaxWidth()
//                                .height(85.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x27E02253)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "每日推荐预算", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                            Text(
                                text = bmiround2.toString() + "千卡",
                                fontSize = 20.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun progressbar(value: Float, color: Color, string1: String, string2: String) {

    Column() {

        var valuee = value
        if( valuee <= 0 )
            valuee = 0f
        if( valuee >= 1)
            valuee =1f
        Canvas(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 25.dp, end = 25.dp)) {

            val canvasWidth = size.width  // 画布的宽
            val canvasHeight = size.height  // 画布的高

            drawLine(
                color = Color(0xFFDDDDDD),
                start = Offset(0f, 0f),
                end = Offset(canvasWidth, 0f),
                strokeWidth = 40f ,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(canvasWidth * valuee, 0f),
                strokeWidth = 40f ,
                cap = StrokeCap.Round
            )
        }
        Row {
            Text(string1, Modifier.padding(start =20.dp))
            Spacer(Modifier.weight(1f))
            Text(string2, Modifier.padding(end =20.dp))
        }
    }
}

fun differentDays(beforeDate: Date, afterDate: Date): Int {
    var result = 0

    //将Date类型转换为Calendar类型
    val beforeCalendar = Calendar.getInstance()
    beforeCalendar.time = beforeDate
    val afterCalendar = Calendar.getInstance()
    afterCalendar.time = afterDate

    //获取日期的DayOfYear（这一天在是这一年的第多少天）
    val beforeDayOfYear = beforeCalendar.get(Calendar.DAY_OF_YEAR)
    val afterDayOfYear = afterCalendar.get(Calendar.DAY_OF_YEAR)

    //获取日期的年份
    val beforeYear = beforeCalendar.get(Calendar.YEAR)
    val afterYear = afterCalendar.get(Calendar.YEAR)

    if (beforeYear == afterYear) {
        //同一年
        result = afterDayOfYear - beforeDayOfYear
    } else {
        //不同一年
        var timeDistance = 0
        for (i in beforeYear until afterYear) {
            timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                //闰年
                366
            } else {
                //不是闰年
                365
            }
        }
        result = timeDistance + (afterDayOfYear - beforeDayOfYear)
    }

    return result
}