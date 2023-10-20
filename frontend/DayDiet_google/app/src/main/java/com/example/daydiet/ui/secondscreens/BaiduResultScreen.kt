package com.example.daydiet.ui.secondscreens


import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.BaiduEntity
import com.example.daydiet.model.entity.RequestEntity
import com.example.daydiet.service.PictureUtil
import com.example.daydiet.ui.camera.RecordCameraActivity
import com.example.daydiet.ui.components.BaiduEntityItem
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.ChatViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.example.daydiet.viewmodel.UserViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BaiduResultScreen(
    baiduViewModel: ChatViewModel = viewModel(),
    userViewModel: UserViewModel,
    recipeviewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    var text by remember {
        mutableStateOf("")
    }
    val user = LocalUserViewModel.current

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val focusRequester = remember { FocusRequester() } //焦点
    val softKeyboard = LocalSoftwareKeyboardController.current //软键盘

    var number_g by remember {
        mutableStateOf("")
    }
    var record_name by remember {
        mutableStateOf("")
    }
    var record_calorie by remember {
        mutableStateOf(0)
    }

    var rec_name by remember {
        mutableStateOf("")
    }
    var calorie by remember {
        mutableStateOf("-1")
    }
    var loading by remember {
        mutableStateOf(0)
    }// 0->正在加载 1->加载完成 2->加载失败
    var net_bad by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){

        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    loading = 0;

                    Log.d("++++6622", "菜品识别")
                    val filePath: String = recipeviewModel.filepath.value // 图片路径
                    val pictureUtil = PictureUtil()
                    var data = pictureUtil.identify(filePath)
                    var result_num = data.get("result_num").toString().toInt()
                    Log.d("++++6622", result_num.toString())
                    var result = data.getJSONArray("result")
                    baiduViewModel.baidu_list.clear()
//                    if(result.getJSONObject(0).get("name").equals("红烧肉")){    //设置为  static
//
//                        Log.d("++++6622", "1111")
//
//
//                        var recname = "红烧肉"
//                        Log.d("++++6622", recname.toString())
//                        var cal = "227"
//                        Log.d("++++6622", cal)
//                        var pro = "0.981"
//                        Log.d("++++6622", pro)
//                        var url = "https://img2.baidu.com/it/u=1311439642,4038204304&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667"
//                        var baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//                        recname = "红烧肉饭"
//                        Log.d("++++6622", recname.toString())
//                        cal = "470"
//                        Log.d("++++6622", cal)
//                        pro = "0.008"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=1640386364,281864274&fm=253&fmt=auto&app=138&f=JPEG?w=490&h=490"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "土猪肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "143"
//                        Log.d("++++6622", cal)
//                        pro = "0.0026"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=3756555263,824456159&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=426"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "干烧肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "225"
//                        Log.d("++++6622", cal)
//                        pro = "0.0012"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=3024136719,738143674&fm=253&fmt=auto&app=138&f=JPEG?w=755&h=500"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "金牌坛香肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "200"
//                        Log.d("++++6622", cal)
//                        pro = "0.001"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=2780664082,3191062711&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=668"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//                    }
//
//                    else if(result.getJSONObject(0).get("name").equals("非菜")){    //设置为  static
//
//                        Log.d("++++6622", "2222")
//
//                        var recname = "红烧肉"
//                        Log.d("++++6622", recname.toString())
//                        var cal = "227"
//                        Log.d("++++6622", cal)
//                        var pro = "0.981"
//                        Log.d("++++6622", pro)
//                        var url = "https://img2.baidu.com/it/u=1311439642,4038204304&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667"
//                        var baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//                        recname = "红烧肉饭"
//                        Log.d("++++6622", recname.toString())
//                        cal = "470"
//                        Log.d("++++6622", cal)
//                        pro = "0.008"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=1640386364,281864274&fm=253&fmt=auto&app=138&f=JPEG?w=490&h=490"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "土猪肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "143"
//                        Log.d("++++6622", cal)
//                        pro = "0.0026"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=3756555263,824456159&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=426"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "干烧肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "225"
//                        Log.d("++++6622", cal)
//                        pro = "0.0012"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=3024136719,738143674&fm=253&fmt=auto&app=138&f=JPEG?w=755&h=500"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "金牌坛香肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "200"
//                        Log.d("++++6622", cal)
//                        pro = "0.001"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=2780664082,3191062711&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=668"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//                    }
//
//                    else if(result_num < 5){
//
//                        Log.d("++++6622", "3333")
//
//                        var recname = "红烧肉"
//                        Log.d("++++6622", recname.toString())
//                        var cal = "227"
//                        Log.d("++++6622", cal)
//                        var pro = "0.981"
//                        Log.d("++++6622", pro)
//                        var url = "https://img2.baidu.com/it/u=1311439642,4038204304&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667"
//                        var baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//                        recname = "红烧肉饭"
//                        Log.d("++++6622", recname.toString())
//                        cal = "470"
//                        Log.d("++++6622", cal)
//                        pro = "0.008"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=1640386364,281864274&fm=253&fmt=auto&app=138&f=JPEG?w=490&h=490"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "土猪肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "143"
//                        Log.d("++++6622", cal)
//                        pro = "0.0026"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=3756555263,824456159&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=426"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "干烧肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "225"
//                        Log.d("++++6622", cal)
//                        pro = "0.0012"
//                        Log.d("++++6622", pro)
//                        url = "https://img0.baidu.com/it/u=3024136719,738143674&fm=253&fmt=auto&app=138&f=JPEG?w=755&h=500"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//
//
//                        recname = "金牌坛香肉"
//                        Log.d("++++6622", recname.toString())
//                        cal = "200"
//                        Log.d("++++6622", cal)
//                        pro = "0.001"
//                        Log.d("++++6622", pro)
//                        url = "https://img1.baidu.com/it/u=2780664082,3191062711&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=668"
//                        baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
//                        baiduViewModel.baidu_list.add(baiduEntity)
//                    }
//
//                    else{
//
//                        Log.d("++++6622", "5555")
//
//
//                    }

                    for(i in 0 until result_num){
                        var entity = result.getJSONObject(i)
                        var recname = entity.get("name")
                        Log.d("++++6622", recname.toString())
                        var has_cal = entity.get("has_calorie").toString()
                        var cal = "0"
                        if(has_cal.equals("true")){
                            cal = entity.get("calorie").toString()
                        }
                        Log.d("++++6622", cal)
                        var pro = entity.get("probability")
                        Log.d("++++6622", pro.toString())
                        var url = "https://img0.baidu.com/it/u=3746116361,552742552&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400"
                        var baiduEntity = BaiduEntity(recname.toString(),cal.toString(),pro.toString(),url)
                        baiduViewModel.baidu_list.add(baiduEntity)
                    }

                    Log.d("++++6622", baiduViewModel.baidu_list.size.toString())

                    loading = 1
                    net_bad = false
                }catch (e: Exception) {
                    Log.d("++++", "error")
                    loading = 2
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
                        text = "拍照结果",
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
        sheetContent = {
            val sheetheight = LocalConfiguration.current.screenHeightDp - 130
            Column(modifier = Modifier
                .height(sheetheight.dp)
                .clip(RoundedCornerShape(25.dp))
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0x6643FFD5)
                        )
                    )
                )
            ) {
                if(net_bad == true) {
                    net_bad = false
                    Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                }

                Text(
                    text = buildAnnotatedString {
                        append("○您选择的菜品是:")
                        withStyle(
                            SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ){
                            append(record_name)
                        }
                    },
                    fontSize = 18.sp,
                    color = Color(0xFF666666),
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 18.dp, top = 20.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        append("○其热量为:")
                        withStyle(
                            SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ){
                            append(record_calorie.toString() + " kcal/100g")
                        }
                    },
                    fontSize = 18.sp,
                    color = Color(0xFF666666),
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 18.dp, top = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 10.dp)
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
                            .border(1.dp, Color(0x9900E884), RoundedCornerShape(10.dp))
                            .focusRequester(focusRequester),
                        value = number_g,
                        onValueChange = {
                            number_g = it
                            Log.d("last night", record_name + "###" + record_calorie + "###" + number_g)
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xEE00F894)),
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
                                    Color(0xFF00C864)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (number_g.equals(""))
                                        Text(
                                            text = "输入摄入克重（g）",
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
                            Log.d("last night", record_name + "###" + record_calorie + "###" + number_g)

                            val update = (number_g.toInt() / 100) * record_calorie
                            userViewModel.todayeat.value = userViewModel.todayeat.value + update.toFloat()
                            // TODO   增加饮食记录
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    try {
                                        Log.d("++++9999", "添加今日饮食记录")
                                        val client = OkHttpClient()
                                        val urlBuilder =
                                            "http://124.221.166.194:80/api/v1/update/recent"
                                                .toHttpUrlOrNull()!!
                                                .newBuilder()
//                                            需要（菜谱id或菜谱名）
//                                            所输入的克重在前端进行计算就行了  后端就不存了
//                                            urlBuilder.addQueryParameter("rec_name",拍照结果)
                                        urlBuilder.addQueryParameter("rec_name",record_name)
                                        urlBuilder.addQueryParameter("cal_num",update.toString())
                                        urlBuilder.addQueryParameter("g_num",number_g)
                                        val url = urlBuilder.build()

                                        val builder = FormBody.Builder()
                                        val formBody = builder.build()

                                        val token = user.userInfoManager.token
                                            .firstOrNull()
                                            .toString()
                                        val request = Request
                                            .Builder()
                                            .url(url)
                                            .post(formBody)
                                            .header("token", token)
                                            .build()

                                        val response = client
                                            .newCall(request)
                                            .execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++6666", jsonDataString.toString())
                                        var code = JSONObject(jsonDataString).get("code")
                                        var msg = JSONObject(jsonDataString).get("msg")
                                        Log.d("++++6666", code.toString())
                                        Log.d("++++6666", msg.toString())
                                    }
                                    catch (e: Exception) {

                                    }
                                }
                            }
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00C864),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("添加", fontSize = 17.sp)
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                    var widthdp = LocalConfiguration.current.screenWidthDp
                    widthdp = (widthdp - 60) / 3
                    Column(
                        modifier = Modifier
                            .width(widthdp.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x3322E023))
                            .clickable { number_g = "500" },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "大份", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                    Column(
                        modifier = Modifier
                            .width(widthdp.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x3322E023))
                            .clickable { number_g = "300" },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "中份", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                    Column(
                        modifier = Modifier
                            .width(widthdp.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x3322E023))
                            .clickable { number_g = "150" },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "小份", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                if(number_g != ""){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .height(85.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x3322E023)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "您摄入的热量为", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(8.dp))
                            Text(
                                text = (number_g.toInt() / 100 * record_calorie).toString() + "kcal",
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pictureresult),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(85.dp)
                    .padding(top = 10.dp, bottom = 10.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            if(net_bad == true) {
                net_bad = false
                Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
            }

            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
                                Color(0x6643FFD5)
                            )
                        )
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(loading == 0){
                    Text(
                        text = "加载中 请稍候...",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                else if(loading == 2){
                    Text(
                        text = "加载失败",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                else if(loading == 1){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        itemsIndexed(baiduViewModel.baidu_list){ index, baidu_item ->
                            if(baidu_item.name != "非菜"){
                                Column(
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            if(sheetState.isCollapsed){
                                                record_name = baidu_item.name
                                                record_calorie = baidu_item.calorie.toInt()
                                                number_g = ""
                                                sheetState.expand()
                                                focusRequester.requestFocus()
                                                softKeyboard?.show()
                                                Log.d("last night", record_name + "###" + record_calorie + "###" + number_g)
                                            }
                                            else{
                                                softKeyboard?.hide()
                                                sheetState.collapse()
                                            }
                                        }
                                    }
                                ) {
                                    BaiduEntityItem(baidu_entity = baidu_item)
                                }
                            }
                        }
                    }
                }
                Log.d("++++",loading.toString())
                Spacer(modifier = Modifier.weight(9f))
            }
        }
    }
}