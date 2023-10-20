package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendSetting(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val scope= rememberCoroutineScope()
    val userviewModel = LocalUserViewModel.current
    // TODO 获取用户的参数设置

    var number  by remember {
        mutableStateOf(userviewModel.user_settings.get(0))
    }
    var shicaiqihe by remember {
        mutableStateOf(userviewModel.user_settings.get(1))
    }
    var yingyangjiegou by remember {
        mutableStateOf(userviewModel.user_settings.get(2))
    }
    var teshuxuqiu by remember {
        mutableStateOf(userviewModel.user_settings.get(3))
    }
    var jijieshiling by remember {
        mutableStateOf(userviewModel.user_settings.get(4))
    }
    var yinshipianhao by remember {
        mutableStateOf(userviewModel.user_settings.get(5))
    }

    var net_bad by remember { mutableStateOf(false) }
    var change_acc by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Row(){
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "推荐设置",
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                // TODO 保存推荐参数设置
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        try {
                                            Log.d("++++6666", "修改推荐参数")
                                            val client = OkHttpClient()
                                            val urlBuilder = "http://124.221.166.194:80/api/v1/recommend/change/setting".toHttpUrlOrNull()!!.newBuilder()

                                            val token = userviewModel.userInfoManager.token.firstOrNull().toString()
                                            val url = urlBuilder.build()
                                            var setting = ArrayList<Float>();
                                            setting.add(number)
                                            setting.add(shicaiqihe)
                                            setting.add(yingyangjiegou)
                                            setting.add(teshuxuqiu)
                                            setting.add(jijieshiling)
                                            setting.add(yinshipianhao)
                                            userviewModel.user_settings.clear()
                                            userviewModel.user_settings.add(number)
                                            userviewModel.user_settings.add(shicaiqihe)
                                            userviewModel.user_settings.add(yingyangjiegou)
                                            userviewModel.user_settings.add(teshuxuqiu)
                                            userviewModel.user_settings.add(jijieshiling)
                                            userviewModel.user_settings.add(yinshipianhao)
                                            var gson = Gson();
                                            var settings = gson.toJson(setting)

                                            val body = RequestBody.create("application/json".toMediaTypeOrNull(), settings)

                                            val request = Request.Builder()
                                                .url(url)
                                                .post(body)
                                                .header("token",token)
                                                .build()
                                            val response = client.newCall(request).execute()
                                            val jsonDataString = response.body?.string()
                                            Log.d("++++6666", jsonDataString.toString())
                                            val res = JSONObject(jsonDataString).get("msg")   //res是更新后的6个参数
                                            Log.d("++++6666", res.toString())
                                            if(res.equals("修改成功")){
                                                change_acc = true
                                            }
                                        } catch (e: Exception) {
                                            net_bad = true
                                        }
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF00C864),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 8.dp)
                        ) {
                            if(net_bad == true) {
                                Log.d("====777", "set net_bad false back")
                                net_bad = false
                                Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                            }
                            if(change_acc == true){
                                change_acc = false
                                Toast.makeText(LocalContext.current, "修改成功！", Toast.LENGTH_SHORT).show()
                            }
                            Text("保存", fontSize = 15.sp)
                        }
                    }

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
        LazyColumn(
            modifier = Modifier
                .background(Color(0xFFF3F3F3))
                .fillMaxSize()
        ){
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                ) {
                    Text(
                        text = "默认推荐菜品数目",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = number,
                        onValueChange = {
                            number = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
            item {
                Text(
                    text = "● Tips:",
                    fontSize = 19.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 15.dp)
                )
                Text(
                    text = "下列五个因素的权重会在推荐结果中体现，请按自身需求选择各因素所占权重大小。",
                    fontSize = 17.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 15.dp, top = 5.dp, bottom = 10.dp)
                )
            }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Text(
                        text = "食材契合度",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = shicaiqihe,
                        onValueChange = {
                            shicaiqihe = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Text(
                        text = "营养结构",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = yingyangjiegou,
                        onValueChange = {
                            yingyangjiegou = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Text(
                        text = "特殊需求",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = teshuxuqiu,
                        onValueChange = {
                            teshuxuqiu = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Text(
                        text = "季节时令",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = jijieshiling,
                        onValueChange = {
                            jijieshiling = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)) {
                    Text(
                        text = "饮食偏好",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "0",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 18.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "10",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 18.dp)
                        )
                    }
                    Slider(
                        value = yinshipianhao,
                        onValueChange = {
                            yinshipianhao = it
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00C864),
                            inactiveTrackColor = Color(0x5500C864),
                            activeTrackColor = Color(0xFF00C864),
                            inactiveTickColor = Color.White,
                            activeTickColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            item { Spacer(modifier = Modifier.size(10.dp)) }
        }
    }
}