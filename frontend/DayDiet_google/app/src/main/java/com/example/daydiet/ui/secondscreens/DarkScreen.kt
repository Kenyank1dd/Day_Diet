package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.User
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
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
fun DarkScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    var text by remember { mutableStateOf("") }
    val list = remember { mutableStateListOf<String>() }
    val deletedRouteList = remember { mutableStateListOf<String>() }
    val scope= rememberCoroutineScope()    //协程

    var text_gongyi by remember { mutableStateOf("") }
    var text_nandu by remember { mutableStateOf("") }
    var text_weidao by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "黑暗料理",
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
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
                                Color(0x33FF99FF)
                            )
                        )
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 90.dp, end = 90.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 文本框
                    BasicTextField (
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(70.dp)
                            .border(1.dp, Color(0xFFFF99FF), RoundedCornerShape(10.dp)),
                        value = text_weidao,
                        onValueChange = {
                            text_weidao = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF00C864)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.dark_fork),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(30.dp),
                                    Color(0xFFFF99FF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text_weidao.isEmpty())
                                        Text(
                                            text = "口味",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 文本框
                    BasicTextField (
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(48.dp)
                            .border(1.dp, Color(0xFFFF99FF), RoundedCornerShape(10.dp)),
                        value = text_gongyi,
                        onValueChange = {
                            text_gongyi = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF00C864)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
//                                    imageVector = ImageVector(R.drawable.dark_star),
                                    painter = painterResource(id = R.drawable.dark_bottle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(30.dp),
                                    Color(0xFFFF99FF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text_gongyi.isEmpty())
                                        Text(
                                            text = "工艺",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.sharpicons_cross),
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .height(105.dp)
                            .padding(top = 10.dp, bottom = 10.dp),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )

                    // 文本框
                    BasicTextField (
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(48.dp)
                            .border(1.dp, Color(0xFFFF99FF), RoundedCornerShape(10.dp)),
                        value = text_nandu,
                        onValueChange = {
                            text_nandu = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF00C864)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.dark_star),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(30.dp),
                                    Color(0xFFFF99FF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text_nandu.isEmpty())
                                        Text(
                                            text = "难度",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 60.dp, end = 30.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 文本框
                    BasicTextField (
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                            .height(70.dp)
                            .border(1.dp, Color(0xFFFF99FF), RoundedCornerShape(10.dp)),
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color(0xFF00C864)),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                        .size(30.dp),
                                    Color(0xFFFF99FF)
                                )
                                Box(
                                    Modifier.fillMaxWidth()
                                ) {
                                    if (text.isEmpty())
                                        Text(
                                            text = "请输入食材",
                                            color = Color.Gray,
                                            fontSize = 20.sp
                                        )
                                    innerTextField()
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                list.add(text)
                                text = ""
                            }
                        )
                    )
                    Button(
                        onClick = {
                            list.add(text)
                            text = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF99FF),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("添加", fontSize = 17.sp)
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                LazyColumn(
                    modifier = Modifier.weight(9f)
                ) {
                    itemsIndexed(list) { index, route ->
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
                                    .padding(start = 30.dp, end = 30.dp, top = 3.dp)
                                    .fillMaxWidth(),
                                backgroundColor = Color(0x22D37FE5),
                                elevation = 0.dp) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)) {
                                    Text(
                                        route,
                                        fontSize = 20.sp,
                                        color = Color(0xFF666666),
                                        modifier =Modifier.weight(1F)
                                    )

                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFF666666),
                                        modifier =Modifier.clickable {
                                            list.remove(route)
                                            deletedRouteList.add(route)
                                        }
                                    )
                                }
                            }

                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(15.dp),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .width(270.dp)
                        .height(50.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xAAD39FC5),
                                        Color(0xAAFFB3D5)
                                    )
                                )
                            )
                            .clickable(
                                onClick = {
                                    recipeViewModel.text_gongyi.value = text_gongyi
                                    recipeViewModel.text_nandu.value = text_nandu
                                    recipeViewModel.text_weidao.value = text_weidao
                                    recipeViewModel.ings.clear()
                                    list.forEach{
                                        recipeViewModel.ings.add(it)
                                    }
                                    navHostController.navigate(Destinations.DarkDetailScreen.route)
                                },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Text(
                            text = "前往料理生成",
                            fontSize = 22.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp, end = 25.dp, top = 5.dp, bottom = 5.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(2f))
            }
        }
    }
}