package com.example.daydiet.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.camera.RecordCameraActivity
import com.example.daydiet.ui.components.TopAppBarr
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.heytap.databaseengine.HeytapHealthApi
import com.heytap.databaseengine.apiv2.HResponse
import com.heytap.databaseengine.apiv3.DataReadRequest
import com.heytap.databaseengine.apiv3.data.DataSet
import com.heytap.databaseengine.apiv3.data.DataType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.math.roundToInt


@Composable
fun RecordScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController
) {
    val user = LocalUserViewModel.current
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val userViewModel = LocalUserViewModel.current

    LaunchedEffect(Unit) {
        scope.launch {
            withContext(Dispatchers.IO) {
                if(userViewModel.islogin == true) {
                    try{
                        Log.d("++++666", "获取今日cal_num")
                        val client = OkHttpClient()
                        val urlBuilder =
                            "http://124.221.166.194:80/api/v1/record/cal".toHttpUrlOrNull()!!
                                .newBuilder()

                        val url = urlBuilder.build()
                        val token = user.userInfoManager.token.firstOrNull().toString()
                        Log.d("++++666", token)
                        val request = Request.Builder()
                            .url(url)
                            .header("token", token)
                            .build()

                        val response = client.newCall(request).execute()
                        val jsonDataString = response.body?.string()
                        Log.d("++++666", jsonDataString.toString())
                        val cal_num = JSONObject(jsonDataString).get("data")    //今日已经摄取的卡路里
                        Log.d("++++msg", cal_num.toString())
                        userViewModel.todayeat.value = cal_num.toString().toFloat()
                    }catch (e : Exception){
                        Log.d("+++666","获取今日cal_num失败")
                    }
                }
            }
        }
    }

    val target = Intent(context, RecordCameraActivity::class.java)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("++++666", it.resultCode.toString())
        if(it.resultCode == 1){
            it.data?.apply {
                recipeViewModel.filepath.value = getStringExtra("filepath").toString()
                Log.d("+++RecordScreen", getStringExtra("food-name").toString())
                navHostController.navigate(Destinations.BaiduResultScreen.route)
            }
        }
        else{
            it.data?.getStringExtra("image-base64")?.let { it1 ->
                Log.d("++++666X", it1)
            }
        }
    }

    // ---------------- 计算用户基础代谢 ----------------
    var calbmi = 0f
    var mid = 0f
    if(userViewModel.logged){
        if(userViewModel.userInfo!!.usr_sex == true){
            calbmi = 66 + (13.7f * userViewModel.userInfo!!.new_weight) +
                    (5 * userViewModel.userInfo!!.usr_height) - (6.8f * userViewModel.userInfo!!.usr_age)
        }
        else{
            calbmi = 655 + (9.6f * userViewModel.userInfo!!.new_weight) +
                    (1.7f * userViewModel.userInfo!!.usr_height) - (4.7f * userViewModel.userInfo!!.usr_age)
        }
        mid = (userViewModel.userInfo!!.usr_weight - userViewModel.userInfo!!.tar_weight) * 3850 /
                userViewModel.userInfo!!.tar_time
    }
    else{
        Log.d("++++", "Not Register")
    }
    var calbmi2 = calbmi - mid
    var kcalround = calbmi2.roundToInt()
    // ---------------- 计算用户基础代谢 ----------------

    // ---------------- 用户今日饮食热量 ----------------
    var kcal by remember {
        mutableStateOf(0)
    }
    kcal = userViewModel.todayeat.value.toInt()
    // ---------------- 用户今日饮食热量 ----------------

    // 圆环进度条高度
    var boxWidthDp: Int
    with(LocalConfiguration.current){
        boxWidthDp = (screenWidthDp / 2.5).roundToInt()
    }

    var process_kcal by remember {
        mutableStateOf(0f)
    }

    // TODO 获取是否有未读信息
    var unread_request by remember { mutableStateOf(true) }

    // ---------------- 需要从健康SDK获取的数据 ----------------

    var step_now by remember { mutableStateOf(1300) }
    var step_goal by remember { mutableStateOf(10000) }
    var calorie_now by remember { mutableStateOf(50) }
    var calorie_goal by remember { mutableStateOf(300) }

    // ---------------- 需要从健康SDK获取的数据 ----------------



    // ---------------- 从健康SDK获取数据 ----------------
//    val time = System.currentTimeMillis()
//    val readRequest = DataReadRequest.Builder()
//        .read(DataType.TYPE_DAILY_ACTIVITY_COUNT)
//        .setTimeRange(time - 1, time)
//        .build()
//    HeytapHealthApi.getInstance()
//        .dataApi()
//        .read(readRequest, object : HResponse<List<DataSet?>> {
//            override fun onSuccess(dataSets: List<DataSet?>) {
//                for (dataSet in dataSets) {
//                    showDataSet(dataSet!!)
//                    for (dataPoint in dataSet.dataPoints) {
//                        for (element in dataPoint.dataType.elements) {
//                            if(element.name == "step"){
//                                step_now = dataPoint.getValue(element).asInt()
//                            }
//                            else if(element.name == "step_goal"){
//                                step_goal = dataPoint.getValue(element).asInt()
//                            }
//                            else if(element.name == "calorie"){
//                                calorie_now = dataPoint.getValue(element).asInt()
//                            }
//                            else if(element.name == "calorie_goal"){
//                                calorie_goal = dataPoint.getValue(element).asInt()
//                            }
//                        }
//                    }
//                }
//            }
//            override fun onFailure(i: Int) {
//                Log.d("heytapdatashow", "i: $i")
//            }
//        })
    // ---------------- 从健康SDK获取数据 ----------------

    LaunchedEffect(Unit) {
        scope.launch {
            withContext(Dispatchers.IO){
                Log.d("+++666",userViewModel.islogin.toString())
                if(userViewModel.islogin == true){
                    try{
                        Log.d("++++666", "更新sport和step")
                        val client = OkHttpClient()
                        val urlBuilder = "http://124.221.166.194:80/api/v1/update/sport".toHttpUrlOrNull()!!.newBuilder()
                        urlBuilder.addQueryParameter("sport", calorie_now.toString())
                        urlBuilder.addQueryParameter("step",step_now.toString())

                        val url = urlBuilder.build()
                        val token = user.userInfoManager.token.firstOrNull().toString()
                        val builder = FormBody.Builder()
                        val formBody = builder.build()
                        val request = Request.Builder()
                            .url(url)
                            .post(formBody)
                            .header("token",token)
                            .build()

                        val response = client.newCall(request).execute()
                        val jsonDataString = response.body?.string()
                        val msg = JSONObject(jsonDataString).get("msg")
                        Log.d("++++msg", msg.toString())
                    }catch (e : Exception){
                        Log.d("+++666",e.toString())
                    }
                }
            }
        }
    }

    Column {
        TopAppBarr {
            Spacer(modifier = Modifier.width(20.dp))
            // 搜索按钮
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
                    .border(1.dp, Color(0xFFD5D5D5), RoundedCornerShape(10.dp)),
                color = Color(0xFFF5F5F5)
            ){
                Row(modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clickable(
                        onClick = { navHostController.navigate(Destinations.SearchDetail.route) },
                        // 去除点击效果
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color(0xFF00C864)
                    )
                    Text(
                        "搜索菜谱、食材",
                        color = Color(0xFF808080),
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis// 超出以省略号显示
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color(0xFF00C864),
                modifier = Modifier
                    .size(30.dp)
                    .unread(unread_request)
                    .clickable {
                        navHostController.navigate(Destinations.RequestScreen.route)
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            Surface(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                elevation = 10.dp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
//                    .height(250.dp)
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

                        Spacer(modifier = Modifier.weight(1f))

                        Image(
                            painter = painterResource(id = R.drawable.recordcamera),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(40.dp)
                                .clickable {
                                    if (userViewModel.logged) {
                                        launcher.launch(target)
                                    } else {
                                        navHostController.navigate(Destinations.LoginScreen.route)
                                    }
//                                    launcher.launch(target)
                                }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "快速\n记录",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                    }
                    // 进度条
                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .size(boxWidthDp.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // 圆环
                        if(kcalround == 0){
                            process_kcal = 0f
                        }
                        else{
                            process_kcal = kcal / (kcalround * 1.0f)
                        }

                        CircleRing(boxWidthDp, process_kcal, step_now/(step_goal * 1f), calorie_now/(calorie_goal * 1f))
                    }

                    Row(modifier = Modifier.fillMaxWidth()){

                        Spacer(modifier = Modifier.weight(0.5f))

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
                                    append(kcal.toString())
                                    withStyle(
                                        SpanStyle(
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(0xFF666666)
                                        )
                                    ){
                                        append(" / " + (kcalround + calorie_now/1000).toString() + "千卡")
                                    }
                                },
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
                                    append((calorie_now/1000).toString())
                                    withStyle(
                                        SpanStyle(
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(0xFF666666)
                                        )
                                    ){
                                        append(" / " + (calorie_goal/1000).toString() + "千卡")
                                    }
                                },
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
                                    append(step_now.toString())
                                    withStyle(
                                        SpanStyle(
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color(0xFF666666)
                                        )
                                    ){
                                        append(" / " + (step_goal/1000).toString() + "k步")
                                    }
                                },
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(0.5f))
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    // 家庭成员绿框
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                        elevation = 10.dp,
                        color = Color.White,
                        modifier = Modifier
                            .weight(11f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "家庭成员", fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 2.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0x77E78618))
                                        .clickable(
                                            onClick = {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.FamilyScreen.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "更改\n需求",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 10.dp, end = 2.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 2.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.family_g),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize(),
//                                        .size(90.dp)
//                                            .weight(1f)
//                                            .padding(10.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                    // 喝水记录绿框
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                        elevation = 10.dp,
                        color = Color.White,
                        modifier = Modifier
                            .weight(11f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "喝水记录", fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 2.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier
//                                        .weight(7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF4EB3FF))
                                        .clickable(
                                            onClick = {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.DrinkScreen.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "记录\n喝水",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 10.dp, end = 2.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 2.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                }
//                                Spacer(modifier = Modifier.weight(0.3f))
                                Column(modifier = Modifier.weight(1f)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.cup_g),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .fillMaxSize(),
//                                            .weight(1f)
//                                        .size(90.dp)
//                                            .padding(10.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    // 近日饮食绿框
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                        elevation = 10.dp,
                        color = Color.White,
                        modifier = Modifier
                            .weight(8f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "近日饮食", fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 2.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFFCE081))
                                        .clickable(
                                            onClick = {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.RecentDietScreen.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "查看\n记录",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 10.dp, end = 2.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 2.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.noodles_g),
                                        contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize(),
//                                        .size(90.dp)
//                                        .weight(1f)
//                                        .padding(3.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                    // 体重记录绿框
                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.5.dp, Color(0xFF00C864)),
                        elevation = 10.dp,
                        color = Color.White,
                        modifier = Modifier
                            .weight(11f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "体重记录", fontSize = 22.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.analyze_g),
                                        contentDescription = null,
                                        modifier = Modifier.padding(3.dp).fillMaxSize(),
//                                        .size(100.dp)
//                                            .weight(1f)
//                                            .padding(3.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF00DC64))
                                        .clickable(
                                            onClick = {
                                                if (userViewModel.logged) {
                                                    navHostController.navigate(Destinations.WeightScreen.route)
                                                } else {
                                                    navHostController.navigate(Destinations.LoginScreen.route)
                                                }
                                            },
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "查看统计趋势",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 10.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
                                    )
                                    Text(
                                        text = ">",
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 2.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun showDataSet(dataSet: DataSet) {
    Log.d("heytapdatashow", "time end: " + System.currentTimeMillis())
    for (dataPoint in dataSet.dataPoints) {
        Log.d("heytapdatashow", "data type name: " + dataPoint.dataType.name)
        Log.d("heytapdatashow", "data type start time: " + dataPoint.startTimeStamp)
        for (element in dataPoint.dataType.elements) {
            Log.d("heytapdatashow", "field name: " + element.name)
            Log.d("heytapdatashow", "field format: " + element.format)
            Log.d("heytapdatashow", "value: " + dataPoint.getValue(element))
        }
    }
}

////获取当天每日活动统计数据
//private fun dailyActivityCount() {
//    val time = System.currentTimeMillis()
//    val readRequest = DataReadRequest.Builder()
//        .read(DataType.TYPE_DAILY_ACTIVITY_COUNT)
//        .setTimeRange(time - 1, time)
//        .build()
//    request(readRequest)
//}
//
//private fun request(readRequest: DataReadRequest) {
//    HeytapHealthApi.getInstance()
//        .dataApi()
//        .read(readRequest, object : HResponse<List<DataSet?>> {
//            override fun onSuccess(dataSets: List<DataSet?>) {
//                for (dataSet in dataSets) {
//                    showDataSet(dataSet!!)
//                }
//            }
//
//            override fun onFailure(i: Int) {
//                Log.d("heytapdatashow", "i: $i")
//            }
//        })
//}

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

//        rotate(180f){
//            drawArc(
//                Color(0x22000000),
//                startAngle = -20f,
//                sweepAngle = 220f,
//                useCenter = false,
//                style = Stroke(30f, cap = StrokeCap.Round)
//            )
//        }
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

fun Modifier.unread(show: Boolean): Modifier = this.drawWithContent {
    drawContent()
    if(show) {
        drawCircle(Color.Red, 4.dp.toPx(), Offset(size.width - 1.dp.toPx(), 1.dp.toPx()))
    }
}