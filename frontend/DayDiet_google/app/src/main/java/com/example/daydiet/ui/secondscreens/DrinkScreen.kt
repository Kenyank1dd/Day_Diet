package com.example.daydiet.ui.secondscreens

import android.graphics.Typeface
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.core.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.RecentDiet
import com.example.daydiet.model.entity.ResponseResult
import com.example.daydiet.viewmodel.UserViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun DrinkScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val userviewModel = LocalUserViewModel.current

    var water by remember {
        mutableStateOf(0)
    }

    var buttontext by remember {
        mutableStateOf("记录饮水")
    }

    var text by remember { mutableStateOf("") }

    var net_bad by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val focusRequester = remember { FocusRequester() } //焦点
    val softKeyboard = LocalSoftwareKeyboardController.current //软键盘

    var addacc by remember { mutableStateOf(false) }
    var adderror by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    Log.d("++++999", "获取用户喝水量")
                    val client = OkHttpClient()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/record/water".toHttpUrlOrNull()!!.newBuilder()
                    val url = urlBuilder.build()
                    val token = userviewModel.userInfoManager.token.firstOrNull().toString()    //不知道和   userInfoManager 的token有什么不同
                    val request = Request.Builder()
                        .url(url)
                        .get()
                        .header("token",token)
                        .build()
                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
                    Log.d("++++999", jsonDataString.toString())
                    val data = JSONObject(jsonDataString).get("data")
//                    val gson = Gson()
//                    val data = gson.fromJson(jsonDataString,ResponseResult::class.java)
                    Log.d("++++999", data.toString())
                    water = data.toString().toFloat().toInt()//.data.toString().toFloat().toInt()
                } catch (e: Exception) {
                    Log.d("++++999", e.toString())
                    net_bad = true
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
                        text = "喝水记录",
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
                backgroundColor = Color(0xFF11CCFF),
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
                            Color(0x3333CCFF)
                        )
                    )
                )
            ) {
                if(net_bad == true) {
                    net_bad = false
                    Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                }
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
                            .border(1.dp, Color(0xFF11AAFF), RoundedCornerShape(10.dp))
                            .focusRequester(focusRequester),
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF11CCFF)),
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
                                    Color(0xFF11AAFF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text.isEmpty())
                                        Text(
                                            text = "输入饮水量（ml）",
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
//                                water = water + text.toInt()
//                            }
//                            text = ""
                            scope.launch {
                                softKeyboard?.hide()
                                sheetState.collapse()
                                buttontext = "记录饮水"
                            }
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    try {
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/update/water".toHttpUrlOrNull()!!.newBuilder()
                                        urlBuilder.addQueryParameter("water_num", text.toInt().toString())
                                        val url = urlBuilder.build()
                                        val token = userviewModel.userInfoManager.token.firstOrNull().toString()
                                        val builder = FormBody.Builder()
                                        val formBody = builder.build()
                                        val request = Request.Builder()
                                            .url(url)
                                            .post(formBody)
                                            .header("token",token)
                                            .build()
                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++fuck", jsonDataString.toString())
//                                    val gson = Gson()
//                                    val data = gson.fromJson(jsonDataString,ResponseResult::class.java)
//                                    Log.d("++++fuck", data.data.toString())
//                                    water = data.data.toString().toFloat().toInt()
                                        val data = JSONObject(jsonDataString).get("data")
                                        Log.d("++++999", data.toString())
                                        water = data.toString().toFloat().toInt()
                                        text = ""
                                        addacc = true
                                    } catch (e: Exception) {
                                        adderror = true
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF11AAFF),
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
        Row(
            modifier = Modifier.padding(8.dp).height(60.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = water.toString() + "  ml",
                fontSize = 36.sp,
                color = Color(0xFF11AAFF),
                modifier = Modifier.padding(end = 20.dp)
            )

            Divider(
                color = Color(0xFF11CCFF),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .padding(top = 3.dp, bottom = 3.dp)
            )

            if((2000 - water) >= 0){
                Text(
                    text = "目标2000ml\n剩余" + (2000-water).toString() + "ml",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            else{
                Text(
                    text = "目标2000ml\n剩余0ml",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TimeWave(water)
        }
    }
}

@Composable
fun TimeWave(
    water: Int
) {
    var waterprocess = water
    if(water >= 2000){
        waterprocess = 2000
    }

    val deltaXAnim = rememberInfiniteTransition()
    val dx by deltaXAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing)
        )
    )

    val waveHeight by animateFloatAsState(
        targetValue = 40f,
        animationSpec = TweenSpec(500, easing = LinearEasing)
    )
    val waveWidth = 250
    val bottom = (LocalConfiguration.current.screenHeightDp - 200).toFloat()
    var originalY = (2000 - waterprocess) * bottom / 2000f
    val path = Path()

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            translate(top = 50f) {
                drawPath(path = path, color = Color(0xFF11EEFF))
                path.reset()
                val halfWaveWidth = waveWidth / 2
                path.moveTo(-waveWidth + (waveWidth * dx), originalY.dp.toPx())

                for (i in -waveWidth..(size.width.toInt() + waveWidth) step waveWidth) {
                    path.relativeQuadraticBezierTo(
                        halfWaveWidth.toFloat() / 2,
                        -waveHeight,
                        halfWaveWidth.toFloat(),
                        0f
                    )
                    path.relativeQuadraticBezierTo(
                        halfWaveWidth.toFloat() / 2,
                        waveHeight,
                        halfWaveWidth.toFloat(),
                        0f
                    )
                }
                path.lineTo(size.width, size.height)
                path.lineTo(0f, size.height)
                path.close()
            }
        }
    )
}