package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@Composable
fun InvestScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val user = LocalUserViewModel.current

    val scope = rememberCoroutineScope()

    var textaccount by remember { mutableStateOf("") }

    val typeList = mutableListOf(
        "父亲", "母亲", "儿子", "女儿", "丈夫", "妻子",
        "爷爷", "奶奶", "外公", "外婆", "孙子", "孙女", "外孙", "外孙女",
        "其他"
    )

    var isClick by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }

    var net_bad by remember { mutableStateOf(false) }
    var change_acc by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "关联家人账号",
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
        }
    ) {
        Column(
            modifier = Modifier
                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color(0xFFFFFFFF),
//                            Color(0x6643FFD5)
//                        )
//                    )
                Color.White
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.interlock),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(60.dp)
                    .padding(top = 10.dp, bottom = 1.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "账号",
                    color = Color(0xFF333333),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, bottom = 2.dp, start = 30.dp)
                )

                // 文本框
                BasicTextField(
                    modifier = Modifier
//                        .background(Color.White, RoundedCornerShape(15.dp))
                        .padding(top = 2.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
                        .height(50.dp)
                        .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                    value = textaccount,
                    onValueChange = {
                        textaccount = it
                    },
                    singleLine = true,
                    cursorBrush = SolidColor(Color(0x66FA632F)),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 20.sp
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 10.dp)
                                    .size(30.dp),
                                Color(0x99FA632F)
                            )
                            Box(
                                Modifier.fillMaxWidth()
                            ) {
                                if (textaccount.isEmpty())
                                    Text(
                                        text = "家人悦食账号",
                                        color = Color.Gray,
                                        fontSize = 20.sp
                                    )
                                innerTextField()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
                Text(
                    text = "关系",
                    color = Color(0xFF333333),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 0.dp, bottom = 2.dp, start = 30.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
                        .height(50.dp)
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
                            if (selected.isEmpty()){
                                Text(
                                    text = "选择关系",
                                    color = Color.Gray,
                                    fontSize = 20.sp
                                )
                            }
                            else{
                                Text(
                                    text = selected,
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = isClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        onDismissRequest = { isClick = false },
                        properties = PopupProperties( dismissOnClickOutside = true ),
                        content = {
                            typeList.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        isClick = !isClick
                                        selected = it
                                    },
                                    content = {
                                        Text(text = it)
                                    }
                                )
                            }
                        },
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "Tips:",
                    color = Color(0xFF555555),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 30.dp, top = 8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1、所关联账号需已在悦食注册。",
                    color = Color(0xFF777777),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 40.dp, end = 40.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp)
                        .fillMaxWidth()
                ){
                    Text(
                        text = "2、",
                        color = Color(0xFF777777),
                        fontSize = 16.sp
                    )
                    Text(
                        text = "发送关联请求后,请耐心等待家人账号的同意。",
                        color = Color(0xFF777777),
                        fontSize = 16.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                withContext(Dispatchers.IO){

                                    Log.d("++++666", "关联家庭成员")
                                    val client = OkHttpClient()
                                    val urlBuilder = "http://124.221.166.194:80/api/v1/request/send".toHttpUrlOrNull()!!.newBuilder()

                                    Log.d("++++666",selected)
                                    urlBuilder.addQueryParameter("phone",textaccount)
                                    urlBuilder.addQueryParameter("req_msg",selected)
                                    Log.d("++++666",selected)
                                    val url = urlBuilder.build()
                                    val token = user.userInfoManager.token.firstOrNull().toString()
                                    Log.d("++++666", url.toString())

                                    val builder = FormBody.Builder()
                                    val formBody = builder.build()

                                    val request = Request.Builder()
                                        .url(url)
                                        .post(formBody)
                                        .header("token",token)
                                        .build()

                                    val response = client.newCall(request).execute()
                                    val jsonDataString = response.body?.string()
                                    Log.d("++++666", jsonDataString.toString())

                                    val code = JSONObject(jsonDataString).get("code").toString().toInt()     //获取返回信息
                                    Log.d("++++666", code.toString())
                                    if(code == 200){
                                        //发送请求成功
                                        change_acc = true
                                    }
                                    else{
                                        //发送请求失败
                                        net_bad = true
                                    }
                                }
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFAA37F),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {

                        if(net_bad == true) {
                            Log.d("====777", "set net_bad false back")
                            net_bad = false
                            Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                        }
                        if(change_acc == true){
                            change_acc = false
                            Toast.makeText(LocalContext.current, "已成功发送请求！", Toast.LENGTH_SHORT).show()
                        }
                        Text("发送关联请求", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}