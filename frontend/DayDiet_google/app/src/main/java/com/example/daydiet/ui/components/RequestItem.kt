package com.example.daydiet.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.RequestEntity
import com.example.daydiet.viewmodel.FamilyViewModel
import com.example.daydiet.viewmodel.UserViewModel
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

@Composable
fun RequestItem(
    request: RequestEntity,
    navHostController: NavHostController
) {

    val user = LocalUserViewModel.current
    val scope = rememberCoroutineScope()

    var type by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.size(8.dp))

    Column(){
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Surface(
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                elevation = 1.dp,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = request.username + "(" + request.id + ")",
                        color = Color(0xFF555555),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "请求关联您的悦食账号",
                        color = Color(0xFF555555),
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("关联关系为")
                            withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)){
                                append(request.relation)
                            }
                        },
                        color = Color(0xFF555555),
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(modifier = Modifier.padding(horizontal = 30.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        Log.d("++++666", "拒绝关联请求")
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/request/process".toHttpUrlOrNull()!!.newBuilder()
                                        urlBuilder.addQueryParameter("type","0")
                                        val url = urlBuilder.build()
                                        val token = user.userInfoManager.token.firstOrNull().toString()
                                        var gson = Gson();
                                        var ans = gson.toJson(request)    //要知道回复的是哪一个request
                                        val body = RequestBody.create("application/json".toMediaTypeOrNull(), ans)
                                        val request = Request.Builder()
                                            .url(url)
                                            .post(body)
                                            .header("token",token)
                                            .build()
                                        Log.d("++++666", request.toString())
                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++666", jsonDataString.toString())
                                        val msg = JSONObject(jsonDataString).get("msg")
                                        Log.d("++++msg", msg.toString())
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFF4B183),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(top = 16.dp)
                        ) {
                            Text("拒绝请求", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        Log.d("++++666", "接受关联请求")
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/request/process".toHttpUrlOrNull()!!.newBuilder()
                                        urlBuilder.addQueryParameter("type","1")
                                        val url = urlBuilder.build()
                                        val token = user.userInfoManager.token.firstOrNull().toString()
                                        var gson = Gson();
                                        var ans = gson.toJson(request)    //要知道回复的是哪一个request
                                        val body = RequestBody.create("application/json".toMediaTypeOrNull(), ans)
                                        val request = Request.Builder()
                                            .url(url)
                                            .post(body)
                                            .header("token",token)
                                            .build()
                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        val msg = JSONObject(jsonDataString).get("msg")
                                        Log.d("++++msg", msg.toString())
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFA3C7E7),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(top = 16.dp)
                        ) {
                            Text("接受请求", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }


            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF60ED9D),
                modifier = Modifier
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = "关联请求",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                )
            }
        }
    }
}