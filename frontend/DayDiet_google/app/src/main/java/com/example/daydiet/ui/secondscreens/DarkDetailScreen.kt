package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@Composable
fun DarkDetailScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val scope = rememberCoroutineScope()

    var loading by remember {
        mutableStateOf(true)
    }

    var loading_plate by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    loading = true

                    val client = OkHttpClient.Builder()
                        .connectTimeout(600, TimeUnit.SECONDS)
                        .readTimeout(600, TimeUnit.SECONDS)
                        .build()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/generate/recipe".toHttpUrlOrNull()!!.newBuilder()
                    urlBuilder.addQueryParameter("gongyi",recipeViewModel.text_gongyi.value)
                    urlBuilder.addQueryParameter("nandu",recipeViewModel.text_nandu.value)
                    urlBuilder.addQueryParameter("weidao",recipeViewModel.text_weidao.value)

                    val url = urlBuilder.build()
                    var gson = Gson();
                    var ings = gson.toJson(recipeViewModel.ings)
                    Log.d("++++dddd", ings)
                    val body = RequestBody.create("application/json".toMediaTypeOrNull(), ings)
                    val request = Request.Builder()
                        .url(url)
                        .post(body)
                        .build()
                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
                    Log.d("++++dddd", jsonDataString.toString())

                    val data = JSONObject(jsonDataString).get("data").toString()
                    Log.d("++++dddd", data)

                    val recname = JSONObject(data).get("name").toString()
                    val list1 = JSONObject(data).getJSONArray("material")
                    val list2 = JSONObject(data).getJSONArray("step")
                    val list3 = JSONObject(data).getJSONArray("tips")
                    recipeViewModel.step.clear()
                    recipeViewModel.material.clear()
                    recipeViewModel.tips.clear()

                    recipeViewModel.updatedark(recname)
                    for(i in 0 until list1.length()){
                        val temp = list1.get(i).toString()
                        recipeViewModel.material.add(temp)
                    }
                    for(i in 0 until list2.length()){
                        val temp = list2.get(i).toString()
                        recipeViewModel.step.add(temp)
                    }
                    for(i in 0 until list3.length()){
                        val temp = list3.get(i).toString()
                        recipeViewModel.tips.add(temp)
                    }

                    Log.d("++++dddd", recname)

                    Log.d("++++dddd", recipeViewModel.darkrecipe.toString())
                    Log.d("++++dddd", recipeViewModel.step.toString())
                    Log.d("++++dddd", recipeViewModel.material.toString())
                    loading = false

                    Log.d("++++dddd", "loading false")
                } catch (e: Exception) {
                    recipeViewModel.net_bad.value = true
                }

                recipeViewModel.createurl.value = ""
                try {
                    loading_plate = true

                    val client = OkHttpClient()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/generate/plating".toHttpUrlOrNull()!!.newBuilder()
                    urlBuilder.addQueryParameter("rec_name",recipeViewModel.darkrecipe.value)
                    urlBuilder.addQueryParameter("index","0")
                    val url = urlBuilder.build()
                    Log.d("++++dddd", recipeViewModel.darkrecipe.value)
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
                    Log.d("++++dddd", jsonDataString.toString())
                    val json = JSONObject(jsonDataString).get("data").toString()
                    Log.d("++++dddd", json)
                    recipeViewModel.createurl.value = json

                    loading_plate = false

                } catch (e: Exception) {
                    recipeViewModel.net_bad.value = true
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
                        text = "料理生成",
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

        if(recipeViewModel.net_bad.value == true) {
            recipeViewModel.net_bad.value = false
            Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.innovativecuisine),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(75.dp)
                    .padding(top = 10.dp, bottom = 10.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            if(loading == true){
                Text(text = "加载中，请稍后...", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
            else{
                Text(text = recipeViewModel.darkrecipe.value, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                LazyColumn(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFFFFF),
                                    Color(0x88FFC3E5)
                                )
                            )
                        )
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(0.5.dp, Color(0xFFFEBEFC)),
                                elevation = 1.dp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxSize()
                                ){
                                    Spacer(modifier = Modifier.height(16.dp))
                                    recipeViewModel.material.forEach {
                                        Text(
                                            text = "-" + it,
                                            color = Color(0xFF555555),
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(start = 15.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFEBEFC),
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Text(
                                    text = "食材",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    item {
                        recipeViewModel.step.forEachIndexed { index, s ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                Surface(
                                    shape = RoundedCornerShape(15.dp),
                                    border = BorderStroke(0.5.dp, Color(0xFFFEBEFC)),
                                    elevation = 1.dp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .fillMaxSize()
                                    ){
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = s,
                                            color = Color(0xFF777777),
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFFEBEFC),
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                ) {
                                    Text(
                                        text = "步骤" + (index + 1).toString(),
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(0.5.dp, Color(0xFFFEBEFC)),
                                elevation = 1.dp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxSize()
                                ){
                                    Spacer(modifier = Modifier.height(8.dp))
                                    recipeViewModel.tips.forEachIndexed { index, tip->
                                        Text(
                                            text = (index + 1).toString() + "、" + tip,
                                            color = Color(0xFF555555),
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(start = 15.dp, top = 3.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFEBEFC),
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Text(
                                    text = "小贴士",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(0.5.dp, Color(0xFFFEBEFC)),
                                elevation = 1.dp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                if(loading_plate == true){
                                    Column(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Text(text = "加载中，请稍后...", fontSize = 20.sp, fontWeight = FontWeight.Normal)
                                    }
                                }
                                else{
                                    Column(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        AsyncImage(
                                            model = "http://124.221.166.194:8080" + recipeViewModel.createurl.value,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .padding(
                                                    start = 20.dp,
                                                    end = 20.dp,
                                                    top = 15.dp,
                                                    bottom = 15.dp
                                                )
                                                .aspectRatio(1 / 1f)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                    }
                                }

                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFEBEFC),
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            ) {
                                Text(
                                    text = "创意呈现",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

        }
    }
}