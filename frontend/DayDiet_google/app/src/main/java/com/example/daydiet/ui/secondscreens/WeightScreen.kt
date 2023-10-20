package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.Domain.WeightRecord
import com.example.daydiet.model.entity.RecentDiet
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeDetailViewModel
import com.example.daydiet.viewmodel.UserViewModel
import com.google.accompanist.insets.statusBarsPadding
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
import java.util.ArrayList
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun WeightScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val user = LocalUserViewModel.current

    var BMI = user.userInfo!!.new_weight /
            ( (user.userInfo!!.usr_height / 100f) * (user.userInfo!!.usr_height / 100f) )
    var BMIround = BMI.roundToInt()

    var buttontext by remember {
        mutableStateOf("记录体重")
    }

    var text by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    val focusRequester = remember { FocusRequester() } // 焦点
    val softKeyboard = LocalSoftwareKeyboardController.current // 软键盘

    var addacc by remember { mutableStateOf(false) }
    var adderror by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    Log.d("++++fuck", "查询体重记录")
                    val client = OkHttpClient()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/record/weight".toHttpUrlOrNull()!!.newBuilder()
                    val url = urlBuilder.build()
                    val token = user.userInfoManager.token.firstOrNull().toString()
                    val request = Request.Builder()
                        .url(url)
                        .get()
                        .header("token",token)
                        .build()
                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
                    Log.d("++++fuck", jsonDataString.toString())
                    val record = JSONObject(jsonDataString).getJSONArray("data")
                    var records = ArrayList<WeightRecord>()
                    for(i in 0 until record.length()){
                        var jsonObject = record.getJSONObject(i)
                        records.add(Gson().fromJson(jsonObject.toString(), WeightRecord::class.java))
                    }


//                    user.weightlist.clear()
//                    Log.d("++++fuck", records.toString())
//                    for(i in 0 until record.length()){
//                        user.weightlist.add(records.get(i).usr_weight.toFloat())
//                    }
//                    Log.d("++++fuck", user.weightlist.toString())


                    user.weightlist.clear()
                    user.weeks.clear()
                    Log.d("++++999", records.toString())

                    var len = record.length().toInt() - 1
                    for(i in 0 until record.length()){
                        user.weightlist.add(records.get(len - i).usr_weight.toFloat())
                        var t1 = records.get(len - i).date.substring(5,7)
                        var t2 = records.get(len - i).date.substring(8,10)
                        var month = (t1.get(0) - '0') * 10 + (t1.get(1) - '0')
                        var day = (t2.get(0) - '0') * 10 + (t2.get(1) - '0')
                        user.weeks.add(month.toString() + "." + day.toString())
                    }

                    Log.d("++++999", user.weightlist.toString())
                    Log.d("++++999", user.weeks.toString())



                } catch (e: Exception) {

                }
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "体重记录",
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {  Text(text = buttontext, fontSize = 18.sp, modifier = Modifier.padding(start = 12.dp, end = 12.dp)) },
                onClick = {
                    scope.launch {
                        if(sheetState.isCollapsed){
                            sheetState.expand()
                            focusRequester.requestFocus()
                            softKeyboard?.show()
                            buttontext = "收起添加"
                        }
                        else{
                            softKeyboard?.hide()
                            sheetState.collapse()
                            buttontext = "记录体重"
                        }
                    }
                },
//                icon ={ Icon(Icons.Filled.Check,"")},
                modifier = Modifier.padding(bottom = 120.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        sheetContent = {

            val sheetheight = LocalConfiguration.current.screenHeightDp - 270

            Column(modifier = Modifier
                .height(sheetheight.dp)
                .clip(RoundedCornerShape(25.dp))
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
//                                Color(0x3333CCFF)
                            Color(0x6643FFD5)
                        )
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 30.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 文本框
                    BasicTextField(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(70.dp)
                            .border(1.dp, Color(0xFF13AF85), RoundedCornerShape(10.dp))
                            .focusRequester(focusRequester),
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF23CFA5)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 15.dp, end = 10.dp)
                                        .size(30.dp),
                                    Color(0xFF13AF85)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text.isEmpty())
                                        Text(
                                            text = "请输入体重（kg）",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),

                    )
                    Button(
                        onClick = {
//                            if(text != ""){
//                                user.weightlist.removeLast()
//                                user.weightlist.add(text.toFloat())
//                            }
//                            text = ""

                            scope.launch {
                                softKeyboard?.hide()
                                sheetState.collapse()
                                buttontext = "记录体重"
                            }

                            scope.launch {
                                withContext(Dispatchers.IO){
                                    try {
                                        Log.d("++++fuck", "修改体重记录")
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/update/weight".toHttpUrlOrNull()!!.newBuilder()
                                        urlBuilder.addQueryParameter("weight",text)

                                        val url = urlBuilder.build()

                                        Log.d("++++fuck", text)

                                        val builder = FormBody.Builder()
                                        val formBody = builder.build()

                                        val token = user.userInfoManager.token.firstOrNull().toString()
                                        val request = Request.Builder()
                                            .url(url)
                                            .post(formBody)
                                            .header("token",token)
                                            .build()

                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()

                                        Log.d("++++fuck", jsonDataString.toString())

                                        val record = JSONObject(jsonDataString).getJSONArray("data")
                                        var records = ArrayList<WeightRecord>()
                                        for(i in 0 until record.length()){
                                            var jsonObject = record.getJSONObject(i)
                                            records.add(Gson().fromJson(jsonObject.toString(), WeightRecord::class.java))
                                        }
//                                            user.weightlist.clear()
//                                            Log.d("++++6666", records.toString())
//
//                                            for(i in 0 until record.length()){
//                                                user.weightlist.add(records.get(i).usr_weight.toFloat())
//                                            }
//                                            text = ""
//                                            Log.d("++++6666", user.weightlist.toString())

                                        user.weightlist.clear()
                                        user.weeks.clear()
                                        Log.d("++++999", records.toString())

                                        var len = record.length().toInt() - 1
                                        for(i in 0 until record.length()){
                                            user.weightlist.add(records.get(len - i).usr_weight.toFloat())
                                            var t1 = records.get(len - i).date.substring(5,7)
                                            var t2 = records.get(len - i).date.substring(8,10)
                                            var month = (t1.get(0) - '0') * 10 + (t1.get(1) - '0')
                                            var day = (t2.get(0) - '0') * 10 + (t2.get(1) - '0')
                                            user.weeks.add(month.toString() + "." + day.toString())
                                        }

                                        Log.d("++++999", user.weightlist.toString())
                                        Log.d("++++999", user.weeks.toString())
                                        text = ""
                                        addacc = true
                                    } catch (e: Exception){
                                        adderror = true
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF13AF85),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("添加", fontSize = 17.sp)
                    }
                }

                if(addacc == true) {
                    addacc = false
                    Toast.makeText(LocalContext.current, "添加成功", Toast.LENGTH_SHORT).show()
                }
                if(adderror == true) {
                    adderror = false
                    Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
//            Text(
//                text = user.UserDetial.name,
//                color = Color.Black,
//                fontSize = 23.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(start = 35.dp, top = 8.dp, bottom = 8.dp)
//            )
            Spacer(modifier = Modifier.size(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                Column(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.age),
                        contentDescription = null,
                        tint = Color(0xFF00C688),
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "年龄", fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = user.userInfo!!.usr_age.toString(), fontSize = 13.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bmi),
                        contentDescription = null,
                        tint = Color(0xFF00C688),
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "BMI", fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = BMIround.toString(), fontSize = 13.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.height),
                        contentDescription = null,
                        tint = Color(0xFF00C688),
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "身高", fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = user.userInfo!!.usr_height.toString() + "cm", fontSize = 13.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Divider()

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(0.5.dp, Color(0x4400C864)),
                elevation = 5.dp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "体重折线",
                            color = Color(0xFF333333),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                            .weight(9f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 3.dp, end = 3.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            var max = user.weightlist.maxOrNull()
                            if (max != null) {
                                max = max + 10f
                            }
                            else{
                                max = 10f
                            }
                            var min = user.weightlist.minOrNull()
                            if (min != null) {
                                if(min - 10f < 0){
                                    min = 0f
                                }
                                else{
                                    min = min - 10f
                                }
                            }
                            else{
                                min = 0f
                            }
                            var midspace: Int = ((max - min) / 5).toInt()
                            var uptext: Int = max.toInt()
                            for( i in 0 until 6 ){
                                Text(
                                    text = uptext.toString() + "kg",
                                    color = Color(0xff999999),
                                    fontSize = 12.sp,
//                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                                uptext = uptext - midspace
                            }
                        }
                        ChartView(points = user.weightlist)
                    }
//                    ChartView(points = user.weightlist)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp)
                            .weight(2f)
                    ) {
                        Spacer(modifier = Modifier.size(20.dp))
                        user.weeks.forEach {
                            Text(
                                text = it,
                                color = Color(0xff999999),
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChartView(points: List<Float>) {
    //每一行的高度
    val heightForRow = 34
    //总行数
    val countForRow = 5
    //小圆圈半径
    val circleRadius = 2.5
    //画布宽度
    val canvasWidth = LocalConfiguration.current.screenWidthDp - 8 * 2 - 50
    //画布高度
    val canvasHeight = heightForRow * countForRow + circleRadius * 2
    //每8 dp 代表1积分 每一行 3积分
    var max = points.maxOrNull()
    if (max != null) {
        max = max + 10f
    }
    else{
        max = 10f
    }
    var min = points.minOrNull()
    if (min != null) {
        if(min - 10f < 0){
            min = 0f
        }
        else{
            min = min - 10f
        }
    }
    else{
        min = 0f
    }
    val perY = ( heightForRow * countForRow )/( max - min )
//    val perY = 8 //24 * 5 /15
    //七平分宽度
    val averageOfWidth = canvasWidth / 7

    Canvas(modifier = Modifier.size(width = canvasWidth.dp, canvasHeight.dp), onDraw = {
        //背景横线
        for (index in 0..countForRow) {
            val startY = (index * heightForRow.toFloat() + circleRadius).dp.toPx()
            val endX = size.width
            val endY = startY
            drawLine(Color(0xFFEEEEEE),
                start = Offset(x = 0f, y = startY),
                end = Offset(x = endX, y = endY),
                strokeWidth = 2.5f)
        }

        //画小圆圈、折线
        for (index in 0 until points.count()) {
            Log.e("====", "index:${index} ");
            val centerX = (averageOfWidth * index + averageOfWidth / 2).dp.toPx()
            val centerY =
                (heightForRow * countForRow - (points[index] - min ) * perY + circleRadius).dp.toPx()
            val circleCenter = Offset(centerX, centerY)
            //点
            drawCircle(Color(0xFF009688), radius = circleRadius.dp.toPx(),
                center = circleCenter,
                style = Stroke(width = 5f)
            )
            //线
            if (index < points.count() - 1) {
                val nextPointOffsetX = (averageOfWidth * (index + 1) + averageOfWidth / 2).dp.toPx()
                val nextPointOffsetY =
                    (heightForRow * countForRow - (points[(index + 1)] - min ) * perY + circleRadius).dp.toPx()
                val nextPoint = Offset(nextPointOffsetX, nextPointOffsetY)
                drawLine(Color(0xFF009688), start = circleCenter, end = nextPoint, strokeWidth = 5f)
            }
        }
    })
}