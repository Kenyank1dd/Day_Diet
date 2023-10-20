package com.example.daydiet.ui.secondscreens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.FamilyEntity
import com.example.daydiet.model.entity.RequestEntity
import com.example.daydiet.service.StringUtil
import com.example.daydiet.ui.components.RecipeItem
import com.example.daydiet.ui.components.RequestItem
import com.example.daydiet.viewmodel.FamilyViewModel
import com.example.daydiet.viewmodel.UserViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import com.heytap.databaseengine.HeytapHealthApi
import com.heytap.databaseengine.apiv2.HResponse
import com.heytap.databaseengine.apiv2.auth.AuthResult
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

@Composable
fun RequestScreen(
    familyviewModel: FamilyViewModel = viewModel(),
    navHostController: NavHostController,
    onBack: ()->Unit
) {
    val userviewmodel = LocalUserViewModel.current
    //TODO 获取请求列表
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var type by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){

                Log.d("++++666", "检查是否有家庭成员关联我")
                val client = OkHttpClient()
                val urlBuilder = "http://124.221.166.194:80/api/v1/request/receive".toHttpUrlOrNull()!!.newBuilder()

                val url = urlBuilder.build()
                val token = userviewmodel.userInfoManager.token.firstOrNull().toString()


                val builder = FormBody.Builder()
                val formBody = builder.build()

                val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .header("token",token)
                    .build()
                Log.d("++++666", request.toString())
                val response = client.newCall(request).execute()
                val jsonDataString = response.body?.string()
                Log.d("++++666", jsonDataString.toString())

                var code = JSONObject(jsonDataString).get("code").toString().toInt()
                Log.d("++++666", code.toString())

                if(code == 200){   //成功接收关联请求
                    var requestlist = JSONObject(jsonDataString).getJSONArray("data")
                    userviewmodel.request_list.clear()  //记得清空
                    for(i in 0 until requestlist.length()){
                        var request = requestlist.getJSONObject(i)
                        var request_entity = RequestEntity()
                        request_entity.username = request.get("to_usr_id").toString()
                        request_entity.id = request.get("from_usr_id").toString().toLong()
                        request_entity.relation = request.get("req_msg").toString()
                        request_entity.req_id = request.get("req_id").toString().toLong()
                        userviewmodel.request_list.add(request_entity)
                    }
                    Log.d("++++666", userviewmodel.request_list.size.toString())
                }
                else if(code != 200){
                    // 没有关联请求请求
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "关联消息请求",
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
        LazyColumn(
            modifier = Modifier
                .background(
                    Color.White
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.requestmessage),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(top = 10.dp, bottom = 1.dp),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }

            Log.d("+++666","request_list size: " + userviewmodel.request_list.size)
            items(userviewmodel.request_list){ request->
                Log.d("+++666", request.username + "bfubbvirvbui")
                RequestItem(
                    request,
                    navHostController = navHostController
                )
            }
        }
    }
}