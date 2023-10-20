package com.example.daydiet.ui.secondscreens

import android.content.ClipData.Item
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.model.entity.SimpleString
import com.example.daydiet.model.entity.User
import com.example.daydiet.service.StringUtil
import com.example.daydiet.ui.components.FoodItem
import com.example.daydiet.ui.components.RecipeItem
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.DetectResultViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.common.reflect.TypeToken
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
import java.util.concurrent.TimeUnit

@Composable
fun DetectResultScreen(
    navHostController: NavHostController,
    onBack: ()->Unit,
    drviewmodel: DetectResultViewModel,
    recipeViewModel: RecipeViewModel
) {
    val deletedRouteList = remember { mutableStateListOf<String>() }
    val scope= rememberCoroutineScope()    //协程
    val user = LocalUserViewModel.current
    val userviewModel = LocalUserViewModel.current
    // TODO 获取用户生成菜谱数目的设置
    var number by remember {
//        mutableStateOf(userviewModel.user_settings.get(0))
        mutableStateOf(3f)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 标题栏
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    text = "扫描结果",
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

        Scaffold(
//            modifier = Modifier
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                Color(0xFFFFFFFF),
//                                      Color(0x3333CCFF)
//                                Color(0x6643FFD5)
//                            )
//                        )
//                    ),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {  Text(text = "前往个性推荐") },
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO){
                                Log.d("++++error", "前往个性推荐")
                                recipeViewModel.list.clear()
                                recipeViewModel.list1.clear()
                                recipeViewModel.list2.clear()
                                recipeViewModel.score_list.clear()
                                recipeViewModel.score_list1.clear()
                                recipeViewModel.score_list2.clear()
                                recipeViewModel.reason.clear()
                                recipeViewModel.reason1.clear()
                                recipeViewModel.reason2.clear()

                                try {
                                    var ing = drviewmodel.list
                                    var gson = Gson();
                                    var ings = gson.toJson(ing)
                                    val client = OkHttpClient.Builder()
                                        .connectTimeout(600,TimeUnit.SECONDS)
                                        .readTimeout(600,TimeUnit.SECONDS)
                                        .build()

                                    val urlBuilder = "http://124.221.166.194:80/api/v1/recommend/recipe".toHttpUrlOrNull()!!.newBuilder()
                                    urlBuilder.addQueryParameter("number_recipes", number.toInt().toString())
                                    val url = urlBuilder.build()
                                    val body = RequestBody.create("application/json".toMediaTypeOrNull(), ings)
                                    val token = user.userInfoManager.token.firstOrNull().toString()
                                    Log.d("++++error", token.toString())
                                    val request = Request.Builder()
                                        .url(url)
                                        .header("token",token)
                                        .post(body)
                                        .build()
                                    val response = client.newCall(request).execute()
                                    val jsonDataString = response.body?.string()
                                    Log.d("++++error", jsonDataString.toString())

                                    var mylist = mutableStateListOf<RecipeEntity>()
                                    var mylist1 = mutableStateListOf<RecipeEntity>()
                                    var mylist2 = mutableStateListOf<RecipeEntity>()
                                    val data = JSONObject(jsonDataString).get("data")

                                    val jsonArray = JSONObject(data.toString()).getJSONArray("recipe")
                                    val num = number.toInt()

                                    for(i in 0 until num){
                                        var jsonObject = jsonArray.getJSONObject(i)
                                        mylist.add(Gson().fromJson(jsonObject.toString(),RecipeEntity::class.java))
                                    }
                                    for(i in num until 2*num){
                                        var jsonObject = jsonArray.getJSONObject(i)
                                        mylist1.add(Gson().fromJson(jsonObject.toString(),RecipeEntity::class.java))
                                    }
                                    for(i in 2*num until 3*num){
                                        var jsonObject = jsonArray.getJSONObject(i)
                                        mylist2.add(Gson().fromJson(jsonObject.toString(),RecipeEntity::class.java))
                                    }

                                    Log.d("++++error",  data.toString())
                                    Log.d("++++error",  jsonArray.toString())
                                    mylist.forEach {
                                        it.rec_url = "http://124.221.166.194:8080" + it.rec_url
                                        recipeViewModel.list.add(it)
                                    }
                                    mylist1.forEach {
                                        it.rec_url = "http://124.221.166.194:8080" + it.rec_url
                                        recipeViewModel.list1.add(it)
                                    }
                                    mylist2.forEach {
                                        it.rec_url = "http://124.221.166.194:8080" + it.rec_url
                                        recipeViewModel.list2.add(it)
                                    }

                                    val scores = JSONObject(data.toString()).getJSONArray("score")
                                    Log.d("++++error",  scores.toString())
                                    for(i in 2 until 7){
                                        val score = (scores.get(i).toString().toFloat()*100)
                                        recipeViewModel.score_list.add(score)
                                    }
                                    for(i in 9 until 14){
                                        val score = (scores.get(i).toString().toFloat()*100)
                                        recipeViewModel.score_list1.add(score)
                                    }
                                    for(i in 16 until 21){
                                        val score = (scores.get(i).toString().toFloat()*100)
                                        recipeViewModel.score_list2.add(score)
                                    }
                                    recipeViewModel.list_index.value = 0

                                    Log.d("++++error", "下边是获取推荐reason")

                                    val reasons = JSONObject(data.toString()).getJSONArray("reason")
                                    Log.d("++++error", reasons.toString())

                                    var stringUtil = StringUtil()

                                    var reason = stringUtil.split2(reasons.get(0).toString())
                                    reason.forEach{
                                        recipeViewModel.reason.add(it)
                                    }
                                    var reason1 = stringUtil.split2(reasons.get(1).toString())
                                    reason1.forEach{
                                        recipeViewModel.reason1.add(it)
                                    }
                                    var reason2 = stringUtil.split2(reasons.get(2).toString())
                                    reason2.forEach{
                                        recipeViewModel.reason2.add(it)
                                    }

                                    Log.d("++++error", recipeViewModel.reason.size.toString())
                                    Log.d("++++error", recipeViewModel.reason1.size.toString())
                                    Log.d("++++error", recipeViewModel.reason2.size.toString())


                                } catch (e: Exception) {
                                    Log.d("++++error", e.toString())
                                    recipeViewModel.net_bad.value = true
                                }
                            }
                        }

                        navHostController.navigate(Destinations.RecommendScreen.route)
                    },
                    icon ={ Icon(Icons.Filled.Check,"")},
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            },
            // 默认在下方
            floatingActionButtonPosition = FabPosition.Center
        ) {
            // Screen content
            LazyColumn(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
//                                Color(0x3333CCFF)
                                Color(0x6643FFD5)
                            )
                        )
                    )
                    .fillMaxSize()
            ) {
                itemsIndexed(drviewmodel.list) { index, route ->
                    AnimatedVisibility(
                        visible = !deletedRouteList.contains(route),
                        enter = expandVertically(),
                        exit = shrinkVertically(
                            animationSpec = tween(
                                durationMillis = 1000,
                            )
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp),
                            backgroundColor = Color(0x5500C864),
                            elevation = 0.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    route,
                                    modifier =Modifier.weight(1F)
                                )

                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                    modifier =Modifier.clickable {
                                        deletedRouteList.add(route)
                                        drviewmodel.list.remove(route)
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(30.dp))
                }

                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                    ) {
                        Text(
                            text = "推荐菜品数目",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(
                                text = "0",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 18.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "10",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(end = 18.dp)
                            )
                        }
                        Slider(
                            value = number,
                            onValueChange = {
                                number = it
                            },
                            valueRange = 0f..10f,
                            steps = 9,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF00C864),
                                inactiveTrackColor = Color(0x5500C864),
                                activeTrackColor = Color(0xFF00C864),
                                inactiveTickColor = Color.White,
                                activeTickColor = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(300.dp))
                }
            }
        }
    }
}