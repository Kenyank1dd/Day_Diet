package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.components.RecipeItem
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@Composable
fun RankScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {
    val scope= rememberCoroutineScope()    //协程

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){
                recipeViewModel.list.clear()
                try {
                    val client = OkHttpClient()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/rank/recipe".toHttpUrlOrNull()!!.newBuilder()
                    val url = urlBuilder.build()
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
                    val gson = Gson()
                    var mylist = mutableStateListOf<RecipeEntity>()
                    Log.d("++++fuck", jsonDataString.toString())
                    val jsonArray = JSONObject(jsonDataString).getJSONArray("data")
                    for(i in 0 until jsonArray.length()){
                        var jsonObject = jsonArray.getJSONObject(i)
                        mylist.add(Gson().fromJson(jsonObject.toString(), RecipeEntity::class.java))
                    }
                    mylist.forEach {
                        it.rec_url = "http://124.221.166.194:8080" + it.rec_url
                        recipeViewModel.list.add(it)
                    }
                    Log.d("++++fuck", jsonDataString.toString())
                    Log.d("++++fuck", mylist.get(0).toString())
                } catch (e: Exception) {
                    Log.d("++++555", "net_bad = true")
                    recipeViewModel.net_bad.value = true
                }
            }
        }
    }

    Column {

        if(recipeViewModel.net_bad.value == true) {
            recipeViewModel.net_bad.value = false
            Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
        }
        // 标题栏
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    text = "食谱排行",
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

        LazyColumn(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0x11FA632F)
                        )
                    )
                )
                .fillMaxSize()
        ){
            item {
                Image(
                    painter = painterResource(id = R.drawable.rankpicture2),
//                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }

            // 食谱列表
            items(recipeViewModel.list){ recipe->
                RecipeItem(
                    recipe,
                    navHostController = navHostController
                )
            }
        }
    }
}