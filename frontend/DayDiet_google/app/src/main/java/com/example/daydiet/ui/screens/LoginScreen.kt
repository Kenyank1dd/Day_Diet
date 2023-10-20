package com.example.daydiet.ui.screens

import android.renderscript.ScriptGroup.Input
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.UserInfoManager
import com.example.daydiet.model.entity.ResponseResult
import com.example.daydiet.model.entity.User
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.UserViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.lang.Thread.sleep

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val userViewModel = LocalUserViewModel.current
    val scope= rememberCoroutineScope()

    userViewModel.account = ""
    userViewModel.password = ""

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(135.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.size(90.dp))

//            OutlinedTextField(
//                value = userViewModel.account,
//                onValueChange = {userViewModel.account = it},
//                shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
//                    unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
//                    errorIndicatorColor = Color.Transparent,//错误时，为红色
//                    disabledIndicatorColor = Color.Transparent,//不可用，灰色
//                    backgroundColor = Color(0x3300C864),
//                    textColor = Color(0xFF666666),
//                    cursorColor = Color(0x7700C864),
//                    focusedLabelColor = Color(0xFF00C864),
//                    unfocusedLabelColor = Color(0xFF00C864)
//                ),
//                modifier = Modifier.fillMaxWidth(),
//                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
//                label = { Text(text = "手机号") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                visualTransformation = VisualTransformation.None
//            )

//            OutlinedTextField(
//                value = userViewModel.password,
//                onValueChange = {userViewModel.password = it},
//                shape = RoundedCornerShape(16.dp,16.dp,16.dp,16.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    focusedIndicatorColor = Color(0x7700C864),//有焦点时 底部指示条为透明
//                    unfocusedIndicatorColor = Color(0x7700C864),//无焦点，为绿色
//                    errorIndicatorColor = Color.Transparent,//错误时，为红色
//                    disabledIndicatorColor = Color.Transparent,//不可用，灰色
//                    backgroundColor = Color(0x3300C864),
//                    textColor = Color(0xFF666666),
//                    cursorColor = Color(0x7700C864),
//                    focusedLabelColor = Color(0xFF00C864),
//                    unfocusedLabelColor = Color(0xFF00C864)
//                ),
//                modifier = Modifier.fillMaxWidth(),
//                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
//                label = { Text(text = "密码") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
//                visualTransformation = PasswordVisualTransformation()
//            )

            Button(
                onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO){
                           userViewModel.login()
                        }
                    }
//                    onBack()
                    Thread.sleep(300)
                    navHostController.navigate(Destinations.HomeFrame.route)
                    Log.d("++++999net", userViewModel.net_ok.toString())
                    Log.d("++++999pass", userViewModel.accounterror.toString())
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 50.dp, end = 50.dp, top = 55.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF20F8C4),
                    contentColor = Color(0xFF00A844),
                ),
            ) {
                Text(text = "游客登录", fontSize = 20.sp)
            }
            Divider(
                color = Color.Black.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 70.dp, start = 30.dp, end = 30.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically){
                Text("本版Demo仅供展示使用", color = Color.Gray)
                Spacer(modifier = Modifier.size(15.dp))
//                TextButton(
//                    onClick = {
//                        navHostController.navigate(Destinations.RegisterScreen.route)
//                    },
//                    colors = ButtonDefaults.textButtonColors(
//                        contentColor = Color(0xFF007814)
//                    )
//                ) {
//                    Text(text = "注册")
//                }
            }
        }
    }
}