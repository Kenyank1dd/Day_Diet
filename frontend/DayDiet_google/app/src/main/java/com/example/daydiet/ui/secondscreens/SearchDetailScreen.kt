package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.model.entity.ResponseResult
import com.example.daydiet.model.entity.User
import com.example.daydiet.ui.components.*
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchDetailScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {
    val focusRequester = remember { FocusRequester() } //焦点
    val softKeyboard = LocalSoftwareKeyboardController.current //软键盘
    val scope= rememberCoroutineScope()    //协程

    LaunchedEffect(key1 = Unit, block = {
        focusRequester.requestFocus()//首次进入和重组页面请求焦点
        softKeyboard?.show()//首次进入页面弹出键盘，注意必须先获取焦点才能弹出键盘成功
    })

    var text by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(10.dp))
                // 返回键
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .clickable { onBack() }
                        .padding(8.dp)
                )
                // 搜索框
                BasicTextField(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(4.dp)
                        .height(40.dp)
                        .border(1.dp, Color(0xFF00C864), RoundedCornerShape(10.dp))
                        .focusRequester(focusRequester),
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    singleLine = true,
                    cursorBrush = SolidColor(Color(0xFF00C864)),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                tint = Color(0xFF00C864)
                            )
                            Box(
                                Modifier.fillMaxWidth()
                            ) {
                                if (text.isEmpty())
                                    Text(
                                        text = "请输入菜名",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                innerTextField()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(
                        onGo = { navHostController.navigate(Destinations.SearchResult.route) }
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // 搜索
                Text(
                    text = "搜索",
                    color = Color(0xFF00C864),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    recipeViewModel.list.clear()
                                    try{
                                        Log.d("++++fuck", "搜索食谱功能")
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/search/recipe".toHttpUrlOrNull()!!.newBuilder()

                                        urlBuilder.addQueryParameter("searchtxt",text)
                                        val url = urlBuilder.build()
                                        Log.d("++++fuck", text.toString())
                                        val request = Request.Builder()
                                            .url(url)
                                            .build()

                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++fuck", jsonDataString.toString())
                                        val gson = Gson()

                                        var mylist = mutableStateListOf<RecipeEntity>()

                                        val jsonArray = JSONObject(jsonDataString).getJSONArray("data")
                                        for(i in 0 until jsonArray.length()){
                                            var jsonObject = jsonArray.getJSONObject(i)
                                            mylist.add(Gson().fromJson(jsonObject.toString(), RecipeEntity::class.java))
                                        }
                                        mylist.forEach {
                                            it.rec_url = "http://124.221.166.194:8080" + it.rec_url
                                            Log.d("++++555", it.rec_name)
                                            recipeViewModel.list.add(it)
                                        }
                                        Log.d("++++fuck", jsonDataString.toString())
                                        Log.d("++++fuck", mylist.get(0).toString())
                                        recipeViewModel.net_bad.value = false
                                    }catch (e: Exception){
                                        Log.d("++++555", "net_bad = true")
                                        recipeViewModel.net_bad.value = true
                                    }
                                }
                            }
                            navHostController.navigate(Destinations.SearchResult.route)
                        },
                    fontSize = 18.sp
                )

                Log.d("++++666", recipeViewModel.net_bad.value.toString())
//                if(recipeViewModel.net_bad == true) {
//                    recipeViewModel.net_bad = false
//                    Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
//                }

                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    ) {
        Column(modifier = Modifier.background(Color.White).fillMaxSize()) {

        }
    }
}
