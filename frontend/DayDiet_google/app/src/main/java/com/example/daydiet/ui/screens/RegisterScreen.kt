package com.example.daydiet.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.User
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

@Composable
fun RegisterScreen(
    onBack: ()->Unit
) {
    var textaccount by remember { mutableStateOf("") }
    var textpassword by remember { mutableStateOf("") }
    var textname by remember { mutableStateOf("") }
    var textgender by remember { mutableStateOf("") }
    var textheight by remember { mutableStateOf("") }
    var textweight by remember { mutableStateOf("") }
    var textage by remember { mutableStateOf("") }
    var textweightgoal by remember { mutableStateOf("") }
    var textperiod by remember { mutableStateOf("") }

    val userViewModel = LocalUserViewModel.current
    var recipeViewModel: RecipeViewModel = viewModel()
    val scope= rememberCoroutineScope()

    var have_empty by remember { mutableStateOf(false) }
    var have_registed by remember { mutableStateOf(false) }
    var net_bad by remember { mutableStateOf(false) }
    var register_acc by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        LazyColumn(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.applogo),
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(20.dp))
            }

            item {
                OutlinedTextField(
                    value = textaccount,
                    onValueChange = {textaccount = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text(text = "手机号") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textpassword,
                    onValueChange = {textpassword = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    label = { Text(text = "密码") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            item {
                OutlinedTextField(
                    value = textname,
                    onValueChange = {textname = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "用户名") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textgender,
                    onValueChange = {textgender = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "性别") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textheight,
                    onValueChange = {textheight = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "身高") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textweight,
                    onValueChange = {textweight = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "体重") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textage,
                    onValueChange = {textage = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "年龄") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textweightgoal,
                    onValueChange = {textweightgoal = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "目标体重") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                OutlinedTextField(
                    value = textperiod,
                    onValueChange = {textperiod = it},
                    shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
                        unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
                        errorIndicatorColor = Color.Transparent,//错误时，为红色
                        disabledIndicatorColor = Color.Transparent,//不可用，灰色
                        backgroundColor = Color(0x3300C864),
                        textColor = Color(0xFF666666),
                        cursorColor = Color(0x7700C864),
                        focusedLabelColor = Color(0xFF00C864),
                        unfocusedLabelColor = Color(0xFF00C864)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    label = { Text(text = "目标时间段") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    visualTransformation = VisualTransformation.None
                )
            }

            item {
                Button(
                    onClick = {
                        if(
                            textaccount == "" || textpassword == "" ||
                            textname == "" || textgender == "" ||
                            textheight == "" || textweight == "" ||
                            textage == "" || textweightgoal == "" ||
                            textperiod == ""
                        ){
                            have_empty = true
                        }
                        else{
                            var gender = false
                            if(textgender == "男"){ gender = true; }
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    try {
                                        Log.d("++++99", "用户注册功能")
                                        var gson = Gson();
                                        var usr = gson.toJson(
                                            User(
                                                usr_phone = textaccount,
                                                usr_password = textpassword ,
                                                usr_name = textname ,
                                                usr_sex = gender,
                                                usr_height = textheight.toFloat(),
                                                usr_weight = textweight.toFloat() ,
                                                usr_age = textage.toInt() ,
                                                tar_weight = textweightgoal.toFloat(),
                                                tar_time = textperiod.toInt(),
                                                avatar_url = "",
                                            )
                                        )
                                        val client = OkHttpClient()
                                        val builder = FormBody.Builder()
                                        val formBody = builder.build()
                                        val body = RequestBody.create("application/json".toMediaTypeOrNull(), usr)
                                        Log.d("++++99", body.toString())
                                        val request = Request.Builder()
                                            .url("http://124.221.166.194:80/api/v1/register")
                                            .post(body)
                                            .build()
                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++99", jsonDataString.toString())
                                        val code = JSONObject(jsonDataString).get("code").toString()
                                        Log.d("++++99", code)
                                        Log.d("++++99", JSONObject(jsonDataString).get("msg").toString())
                                        if(code.equals("200")){
                                            register_acc = true
                                        }
                                        if(code.equals("201")) {
                                            Log.d("++++99", "pass or account error")
                                            have_registed = true
                                        }
                                    } catch (e: Exception) {
                                        Log.d("+++", "jump catch exception")
                                        net_bad = true
                                    }
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(start = 50.dp, end = 50.dp, top = 8.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF20F8C4),
                        contentColor = Color(0xFF00A844),
                    ),
                ) {
                    if(have_empty == true) {
                        have_empty = false
                        Toast.makeText(LocalContext.current, "注册信息不能有空", Toast.LENGTH_SHORT).show()
                    }
                    if(have_registed == true) {
                        have_registed = false
                        Toast.makeText(LocalContext.current, "手机号已注册", Toast.LENGTH_SHORT).show()
                    }
                    if(net_bad == true) {
                        net_bad = false
                        Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                    }
                    if(register_acc == true){
                        register_acc = false
                        Toast.makeText(LocalContext.current, "注册成功", Toast.LENGTH_SHORT).show()
                        onBack()
                    }
                    Text(text = "注册", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.size(300.dp))
            }
        }
    }
}
