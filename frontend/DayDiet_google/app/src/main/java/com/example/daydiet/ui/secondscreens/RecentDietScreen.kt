package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.Diet_Record
import com.example.daydiet.model.entity.RecentDiet
import com.example.daydiet.model.entity.RecentDietEntity
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.components.RecentDietItem
import com.example.daydiet.ui.components.TopAppBarr
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecentViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RecentDietScreen(
    recentviewModel: RecentViewModel = viewModel(),

    navHostController: NavHostController,
    onBack: ()->Unit
) {
    val userViewModel = LocalUserViewModel.current
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


    val scope= rememberCoroutineScope()
    val userviewModel = LocalUserViewModel.current
//    recentviewModel.list.clear()
//    recentviewModel.list.add(RecentDietEntity("3月30日", "油条、鸡蛋", "重庆小面", "红烧肉"))
//    recentviewModel.list.add(RecentDietEntity("3月31日", "豆腐脑、鸡蛋", "水饺", "风味茄子"))

    var isClick by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(0) }

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    Log.d("++++6666", "查询近日饮食")
                    // 获取当前日期
                    val currentDate = LocalDate.now()
                    // 创建一个包含最近七天日期的列表
                    val dateList = mutableListOf<LocalDate>()
                    for (i in 0..6) {
                        val date = currentDate.minus(i.toLong(), ChronoUnit.DAYS)
                        dateList.add(date)
                    }
                    // 打印最近七天日期
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    recentviewModel.list.clear()
                    Log.d("++++666", "12345")
                    Log.d("++++666", dateList.toString())
                    for (dates in dateList) {

                        val date = dates.toString()
                        Log.d("++++666", "6789")
                        Log.d("++++666", date)
                        val client = OkHttpClient()
                        val urlBuilder = "http://124.221.166.194:80/api/v1/record/recentdiet".toHttpUrlOrNull()!!.newBuilder()
                        urlBuilder.addQueryParameter("date",date)
                        val url = urlBuilder.build()
                        val token = userviewModel.userInfoManager.token.firstOrNull().toString()
                        Log.d("++++666", token)
                        val request = Request.Builder()
                            .url(url)
                            .get()
                            .header("token",token)
                            .build()
                        val response = client.newCall(request).execute()
                        val jsonDataString = response.body?.string()
                        Log.d("++++9999", jsonDataString.toString())
                        val res = JSONObject(jsonDataString).getJSONObject("data")
                        val meal = res.getJSONObject("diet_records")

                        val breakfast = meal.getJSONArray("break_list")
                        val lunch = meal.getJSONArray("lunch_list")
                        val dinner = meal.getJSONArray("dinner_list")
//                        if(breakfast.length() == 0 && lunch.length() == 0 && dinner.length() == 0) continue

                        var breakfasts = ArrayList<Diet_Record>()
                        var lunchs = ArrayList<Diet_Record>()
                        var dinners = ArrayList<Diet_Record>()

                        for(i in 0 until breakfast.length()){
                            var jsonObject = breakfast.getJSONObject(i)
                            breakfasts.add(Gson().fromJson(jsonObject.toString(), Diet_Record::class.java))
                        }

                        for(i in 0 until lunch.length()){
                            var jsonObject = lunch.getJSONObject(i)
                            lunchs.add(Gson().fromJson(jsonObject.toString(), Diet_Record::class.java))
                        }

                        for(i in 0 until dinner.length()){
                            var jsonObject = dinner.getJSONObject(i)
                            dinners.add(Gson().fromJson(jsonObject.toString(), Diet_Record::class.java))
                        }

                        Log.d("++++666", meal.toString())
                        Log.d("++++666", breakfast.toString())
                        Log.d("++++666", breakfasts.toString())

                        var cal_num = res.get("cal_num")
                        var sport = res.get("sport")
                        var step = res.get("step")

                        Log.d("++++666", cal_num.toString())
                        Log.d("++++666", sport.toString())
                        Log.d("++++666", step.toString())

                        if(cal_num.toString().equals("null")) cal_num = 0
                        if(sport.toString().equals("null")) sport = 0
                        if(step.toString().equals("null")) step = 0

                        Log.d("++++666", cal_num.toString())
                        Log.d("++++666", sport.toString())
                        Log.d("++++666", step.toString())

                        var r = RecentDietEntity()
                        r.date = date
                        r.cal_num = cal_num.toString().toInt()
                        r.sport = sport.toString().toInt()
                        r.step = step.toString().toInt()
                        r.breakfast = breakfasts
                        r.lunch = lunchs
                        r.dinner = dinners

                        recentviewModel.list.add(r);
                    }

                } catch (e: Exception) {

                }
            }
        }
    }

//    recentviewModel.list.clear()
//    recentviewModel.list.add(RecentDietEntity("3月30日", "油条、鸡蛋", "重庆小面", "红烧肉"))
//    recentviewModel.list.add(RecentDietEntity("3月31日", "豆腐脑、鸡蛋", "水饺", "风味茄子"))
//    Log.d("????", recentviewModel.list.size.toString()).

    Column() {
        TopAppBarr {

            Spacer(modifier = Modifier.width(20.dp))

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .clickable {
                        onBack()
                    }
                    .padding(8.dp)
            )
            Text(
                text = "近日记录",
                color = Color.Black,
                fontSize = 18.sp
            )

            Column(
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
                    .height(40.dp)
                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp, end = 10.dp)
                            .size(30.dp),
                        Color(0x99FA632F)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    isClick = !isClick
                                },
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            )
                    ) {
                        if (recentviewModel.list.size.equals(0)){
                            Text(
                                text = "无记录",
                                color = Color.Gray,
                                fontSize = 20.sp
                            )
                        }
                        else{
                            Text(
                                text = recentviewModel.list[selected].date,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                DropdownMenu(
                    expanded = isClick,
                    modifier = Modifier
                        .height(300.dp),
                    onDismissRequest = { isClick = false },
                    properties = PopupProperties( dismissOnClickOutside = true ),
                    content = {
                        recentviewModel.list.forEachIndexed { index, it ->
                            DropdownMenuItem(
                                onClick = {
                                    isClick = !isClick
                                    selected = index
                                },
                                content = {
                                    Text(text = it.date)
                                }
                            )
                        }
                    },
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
//            items(recentviewModel.list){ family->
//                Spacer(modifier = Modifier.size(8.dp))
//                RecentDietItem(family)
//                Spacer(modifier = Modifier.size(4.dp))
//            }
            item {
                Spacer(modifier = Modifier.size(8.dp))
                if(!recentviewModel.list.size.equals(0)){
                    RecentDietItem(
                        recentviewModel.list[selected],
                        bmiround,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}