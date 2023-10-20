package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
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
import okhttp3.internal.wait
import org.json.JSONObject
import java.util.concurrent.TimeUnit

@Composable
fun PlateScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val scope= rememberCoroutineScope()    //协程
    var text by remember {
        mutableStateOf("")
    }

    val color_unselected = Color(0x5527CFF3)
    val color_selected = Color(0xFF27CFF3)

    var color_0 by remember { mutableStateOf(color_selected) }
    var color_1 by remember { mutableStateOf(color_unselected) }
    var color_2 by remember { mutableStateOf(color_unselected) }
    var color_3 by remember { mutableStateOf(color_unselected) }
    var color_4 by remember { mutableStateOf(color_unselected) }
    var color_5 by remember { mutableStateOf(color_unselected) }

    var request_index = mutableStateOf(0)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "创意摆盘",
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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {  Text(text = "前往摆盘生成", fontSize = 18.sp, color = Color.White) },
                onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO){
                            //  前往摆盘生成
                            recipeViewModel.recipename.value = ""
                            recipeViewModel.createurl.value = ""
                            try {
                                recipeViewModel.recipename.value = text
                                val client = OkHttpClient()
                                val urlBuilder = "http://124.221.166.194:80/api/v1/generate/plating".toHttpUrlOrNull()!!.newBuilder()
                                urlBuilder.addQueryParameter("rec_name",text)
                                urlBuilder.addQueryParameter("index", request_index.value.toString())
                                val url = urlBuilder.build()
                                Log.d("++++dddd", text)
                                Log.d("++++dddd", request_index.value.toString())
                                val request = Request.Builder()
                                    .url(url)
                                    .build()
                                val response = client.newCall(request).execute()
                                val jsonDataString = response.body?.string()
                                Log.d("++++dddd", jsonDataString.toString())
                                val json = JSONObject(jsonDataString).get("data").toString()
                                Log.d("++++dddd", json)
                                recipeViewModel.createurl.value = json
                            } catch (e: Exception) {
                                recipeViewModel.net_bad.value = true
                            }
                        }
                    }
                    navHostController.navigate(Destinations.PlateDetailScreen.route)
                },
                icon ={ Icon(Icons.Filled.Check,"", tint = Color.White)},
                modifier = Modifier.padding(bottom = 60.dp),
                backgroundColor = Color(0xFF27CFF3)
            )
        },
        // 默认在下方
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.creativeplating),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
                    .padding(top = 10.dp, bottom = 10.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
                                Color(0x3333CCFF)
                            )
                        )
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 搜索框
                    BasicTextField(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(70.dp)
                            .border(1.dp, Color(0xFF33CCFF), RoundedCornerShape(10.dp)),
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF33CCFF)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.generate),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 15.dp, end = 10.dp)
                                        .size(30.dp),
                                    Color(0xFF33CCFF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text.isEmpty())
                                        Text(
                                            text = "请输入菜名",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                        keyboardActions = KeyboardActions(
                            onGo = { navHostController.navigate(Destinations.PlateDetailScreen.route) }
                        )
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()){
                    item {
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                            var widthdp = LocalConfiguration.current.screenWidthDp
                            widthdp = (widthdp - 60) / 2
                            Column(
                                modifier = Modifier
                                    .padding(start = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_0)
                                    .clickable {
                                        if (color_0 == color_unselected) {
                                            color_0 = color_selected
                                            color_1 = color_unselected
                                            color_2 = color_unselected
                                            color_3 = color_unselected
                                            color_4 = color_unselected
                                            color_5 = color_unselected
                                            request_index.value = 0
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan0),
                                    alignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(end = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_1)
                                    .clickable {
                                        if (color_1 == color_unselected) {
                                            color_0 = color_unselected
                                            color_1 = color_selected
                                            color_2 = color_unselected
                                            color_3 = color_unselected
                                            color_4 = color_unselected
                                            color_5 = color_unselected
                                            request_index.value = 1
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan1),
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                            var widthdp = LocalConfiguration.current.screenWidthDp
                            widthdp = (widthdp - 60) / 2
                            Column(
                                modifier = Modifier
                                    .padding(start = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_2)
                                    .clickable {
                                        if (color_2 == color_unselected) {
                                            color_0 = color_unselected
                                            color_1 = color_unselected
                                            color_2 = color_selected
                                            color_3 = color_unselected
                                            color_4 = color_unselected
                                            color_5 = color_unselected
                                            request_index.value = 2
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan2),
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(end = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_3)
                                    .clickable {
                                        if (color_3 == color_unselected) {
                                            color_0 = color_unselected
                                            color_1 = color_unselected
                                            color_2 = color_unselected
                                            color_3 = color_selected
                                            color_4 = color_unselected
                                            color_5 = color_unselected
                                            request_index.value = 3
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan3),
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                            var widthdp = LocalConfiguration.current.screenWidthDp
                            widthdp = (widthdp - 60) / 2
                            Column(
                                modifier = Modifier
                                    .padding(start = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_4)
                                    .clickable {
                                        if (color_4 == color_unselected) {
                                            color_0 = color_unselected
                                            color_1 = color_unselected
                                            color_2 = color_unselected
                                            color_3 = color_unselected
                                            color_4 = color_selected
                                            color_5 = color_unselected
                                            request_index.value = 4
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan4),
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(end = 25.dp)
                                    .width(widthdp.dp)
                                    .height(widthdp.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color_5)
                                    .clickable {
                                        if (color_5 == color_unselected) {
                                            color_0 = color_unselected
                                            color_1 = color_unselected
                                            color_2 = color_unselected
                                            color_3 = color_unselected
                                            color_4 = color_unselected
                                            color_5 = color_selected
                                            request_index.value = 5
                                        }
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baipan5),
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.size(150.dp))
                    }
                }
            }
        }
    }
}