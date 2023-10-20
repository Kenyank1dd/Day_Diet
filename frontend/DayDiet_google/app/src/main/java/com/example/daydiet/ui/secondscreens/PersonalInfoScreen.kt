package com.example.daydiet.ui.secondscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.entity.RecentDietEntity
import com.example.daydiet.model.entity.User
import com.example.daydiet.service.StringUtil
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
import org.json.JSONObject

@Composable
fun PersonalInfoScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    val scope= rememberCoroutineScope()
    val userviewModel = LocalUserViewModel.current

    var oldname by remember {
        mutableStateOf("")
    }
    oldname = userviewModel.userInfo?.usr_name.toString()
    var oldgender by remember {
        mutableStateOf("女")
    }
    if(userviewModel.userInfo!!.usr_sex == true){
        oldgender = "男"
    }
    var oldheight by remember {
        mutableStateOf(0f)
    }
    oldheight = userviewModel.userInfo?.usr_height!!
//    val oldheight = userviewModel.userInfo?.usr_height
    var oldnewweight by remember {
        mutableStateOf(0f)
    }
    oldnewweight = userviewModel.userInfo?.new_weight!!
//    val oldnewweight = userviewModel.userInfo?.new_weight
    var oldtarweight by remember {
        mutableStateOf(0f)
    }
    oldtarweight = userviewModel.userInfo?.tar_weight!!
//    val oldtarweight = userviewModel.userInfo?.tar_weight
    var oldtartime by remember {
        mutableStateOf(0)
    }
    oldtartime = userviewModel.userInfo?.tar_time!!
//    val oldtartime = userviewModel.userInfo?.tar_time

    var textname by remember { mutableStateOf("") }
    var textgender by remember { mutableStateOf("") }
    var textheight by remember { mutableStateOf("") }
    var textnewweight by remember { mutableStateOf("") }
    var texttarweight by remember { mutableStateOf("") }
    var texttartime by remember { mutableStateOf("") }

    var isClick by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(-1) }
    val user_tag_list = remember { mutableStateListOf<String>() }
    val deleted_tag_list = remember { mutableStateListOf<String>() }
    val tag_list = mutableListOf(
        "前列腺","糖尿病","高血压","高血脂","冠心病","中风","消化性溃疡","肠炎","防癌抗癌","胆石症","肝硬化","肾炎","痛风","麻疹","结核病",
        "肝炎","动脉硬化","甲状腺","肾炎","贫血","痔疮","月经不调","子宫脱垂","痛经","更年期","小儿遗尿","营养不良","咽炎","关节炎",
        "跌打骨折损伤","骨质疏松","耳鸣","肺气肿","口腔溃疡","尿路结石","支气管炎", "术后"
    )

    var isClick2 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(-1) }
    val user_tag_list2 = remember { mutableStateListOf<String>() }
    val deleted_tag_list2 = remember { mutableStateListOf<String>() }
    val tag_list2 = mutableListOf(
        "白菜","鱼","番茄","鸡蛋","螃蟹","豆芽","牛肉","鸡肉",
        "土豆","苹果","香蕉","胡萝卜","西兰花","辣椒","玉米",
        "黄瓜","橘子","菠萝","菠菜","柠檬","猪肉"
    )
    user_tag_list.clear()
    user_tag_list2.clear()
    userviewModel.allergens.forEach{
        user_tag_list2.add(it)
    }
    userviewModel.diseases.forEach{
        user_tag_list.add(it)
    }

    var net_bad by remember { mutableStateOf(false) }
    var change_acc by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "个人信息",
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
//                .fillMaxSize()
                .background(Color.White)
        ){
            item{
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x55E2E023)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "用户昵称", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = textname,
                                onValueChange = {
                                    textname = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (textname.isEmpty())
                                                oldname?.let { it1 ->
                                                    Text(
                                                        text = it1,
                                                        color = Color.Gray,
                                                        fontSize = 18.sp
                                                    )
                                                }

                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "用户性别", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = textgender,
                                onValueChange = {
                                    textgender = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (textgender.isEmpty())
                                                Text(
                                                    text = oldgender,
                                                    color = Color.Gray,
                                                    fontSize = 18.sp
                                                )
                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "用户身高", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = textheight,
                                onValueChange = {
                                    textheight = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (textheight.isEmpty())
                                                Text(
                                                    text = oldheight.toString() + "厘米",
                                                    color = Color.Gray,
                                                    fontSize = 18.sp
                                                )
                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "最新体重", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = textnewweight,
                                onValueChange = {
                                    textnewweight = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (textnewweight.isEmpty())
                                                Text(
                                                    text = oldnewweight.toString() + "公斤",
                                                    color = Color.Gray,
                                                    fontSize = 18.sp
                                                )
                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "目标体重", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = texttarweight,
                                onValueChange = {
                                    texttarweight = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (texttarweight.isEmpty())
                                                Text(
                                                    text = oldtarweight.toString() + "公斤",
                                                    color = Color.Gray,
                                                    fontSize = 18.sp
                                                )
                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "目标时间", fontSize = 18.sp,
                                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
                            )
                            // 文本框
                            BasicTextField(
                                modifier = Modifier
//                                .weight(1f)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(8.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                value = texttartime,
                                onValueChange = {
                                    texttartime = it
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color(0x66FA632F)),
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 10.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier.fillMaxWidth()
                                        ) {
                                            if (texttartime.isEmpty())
                                                Text(
                                                    text = oldtartime.toString(),
                                                    color = Color.Gray,
                                                    fontSize = 18.sp
                                                )
                                            innerTextField()
                                        }
                                    }
                                },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(15.dp))
            }

            item{
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x55E2E023)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "用户标签",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)
                        )

                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                // 文本框
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .height(45.dp).weight(1f)
//                                        .width(163.dp)
                                        .border(
                                            1.dp, Color(0x99FA632F),
                                            RoundedCornerShape(10.dp)
                                        ),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 8.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier
//                                                    .fillMaxWidth()
                                                .clickable(
                                                    onClick = {
                                                        isClick = !isClick
                                                    },
                                                    indication = null,
                                                    interactionSource = remember {
                                                        MutableInteractionSource()
                                                    }
                                                )
                                        ) {
                                            if (selected.equals(-1)){
                                                Text(
                                                    text = "请选择添加",
                                                    color = Color.Gray,
                                                    fontSize = 20.sp,
                                                )
                                            }
                                            else{
                                                Text(
                                                    text = tag_list[selected],
                                                    color = Color.Black,
                                                    fontSize = 20.sp,
                                                )
                                            }
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = isClick,
                                        modifier = Modifier
                                            .height(250.dp),
                                        onDismissRequest = { isClick = false },
                                        properties = PopupProperties( dismissOnClickOutside = true ),
                                        content = {
                                            tag_list.forEachIndexed { index, it ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        isClick = !isClick
                                                        selected = index
                                                    },
                                                    content = {
                                                        Text(text = it)
                                                    }
                                                )
                                            }
                                        },
                                    )
                                }
                                Button(
                                    onClick = {
                                        if(selected != -1){
                                            user_tag_list.add(tag_list[selected])
                                            selected = -1
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFFAA37F),
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically).padding(10.dp)
                                ) {
                                    Text("添加", fontSize = 15.sp)
                                }
                            }

                            Spacer(modifier = Modifier.size(3.dp))

                            LazyColumn(
                                modifier = Modifier.height(60.dp)
                            ) {
                                if(user_tag_list.isEmpty()){
                                    item {
                                        Card(
                                            modifier = Modifier
                                                .padding(top = 3.dp, start = 10.dp, end = 10.dp)
                                                .fillMaxWidth(),
                                            backgroundColor = Color(0x33FAA37F),
                                            elevation = 0.dp
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    "暂无添加",
                                                    fontSize = 17.sp,
                                                    color = Color(0xFF666666),
                                                    modifier =Modifier.weight(1F)
                                                )
                                            }
                                        }
                                    }
                                }
                                else{
                                    itemsIndexed(user_tag_list) { index, route ->
                                        AnimatedVisibility(
                                            visible = true,
//                                            visible = !deleted_tag_list.contains(route),
                                            enter = expandVertically(),
                                            exit = shrinkVertically(
                                                animationSpec = tween(
                                                    durationMillis = 1000,
                                                )
                                            )
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .padding(top = 3.dp, start = 10.dp, end = 10.dp)
                                                    .fillMaxWidth(),
                                                backgroundColor = Color(0x33FAA37F),
                                                elevation = 0.dp
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(6.dp)
                                                ) {
                                                    Text(
                                                        route,
                                                        fontSize = 17.sp,
                                                        color = Color(0xFF666666),
                                                        modifier =Modifier.weight(1F)
                                                    )

                                                    Icon(
                                                        Icons.Filled.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color(0xFF666666),
                                                        modifier =Modifier.clickable {
                                                            user_tag_list.remove(route)
//                                                            deleted_tag_list.add(route)
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(15.dp))
            }

            item{
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(0.5.dp, Color(0x55E2E023)),
                    elevation = 5.dp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "过敏食材",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)
                        )

                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                // 文本框
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .height(45.dp).weight(1f)
//                                        .width(163.dp)
                                        .border(1.dp, Color(0x99FA632F), RoundedCornerShape(10.dp)),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 8.dp)
                                                .size(30.dp),
                                            Color(0x99FA632F)
                                        )
                                        Box(
                                            Modifier
//                                                    .fillMaxWidth()
                                                .clickable(
                                                    onClick = {
                                                        isClick2 = !isClick2
                                                    },
                                                    indication = null,
                                                    interactionSource = remember {
                                                        MutableInteractionSource()
                                                    }
                                                )
                                        ) {
                                            if (selected2.equals(-1)){
                                                Text(
                                                    text = "请选择添加",
                                                    color = Color.Gray,
                                                    fontSize = 20.sp,
                                                )
                                            }
                                            else{
                                                Text(
                                                    text = tag_list2[selected2],
                                                    color = Color.Black,
                                                    fontSize = 20.sp,
                                                )
                                            }
                                        }
                                    }
                                    DropdownMenu(
                                        expanded = isClick2,
                                        modifier = Modifier
                                            .height(250.dp),
                                        onDismissRequest = { isClick2 = false },
                                        properties = PopupProperties( dismissOnClickOutside = true ),
                                        content = {
                                            tag_list2.forEachIndexed { index, it ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        isClick2 = !isClick2
                                                        selected2 = index
                                                    },
                                                    content = {
                                                        Text(text = it)
                                                    }
                                                )
                                            }
                                        },
                                    )
                                }
                                Button(
                                    onClick = {
                                        if(selected2 != -1){
                                            user_tag_list2.add(tag_list2[selected2])
                                            selected2 = -1
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFFFAA37F),
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically).padding(10.dp)
                                ) {
                                    Text("添加", fontSize = 15.sp)
                                }
                            }

                            Spacer(modifier = Modifier.size(3.dp))

                            LazyColumn(
                                modifier = Modifier.height(60.dp)
                            ) {
                                if(user_tag_list2.isEmpty()){
                                    item {
                                        Card(
                                            modifier = Modifier
                                                .padding(top = 3.dp, start = 10.dp, end = 10.dp)
                                                .fillMaxWidth(),
                                            backgroundColor = Color(0x33FAA37F),
                                            elevation = 0.dp
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    "暂无添加",
                                                    fontSize = 17.sp,
                                                    color = Color(0xFF666666),
                                                    modifier =Modifier.weight(1F)
                                                )
                                            }
                                        }
                                    }
                                }
                                else{
                                    itemsIndexed(user_tag_list2) { index, route ->
                                        AnimatedVisibility(
                                            visible = true,
//                                            visible = !deleted_tag_list2.contains(route),
                                            enter = expandVertically(),
                                            exit = shrinkVertically(
                                                animationSpec = tween(
                                                    durationMillis = 1000,
                                                )
                                            )
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .padding(top = 3.dp, start = 10.dp, end = 10.dp)
                                                    .fillMaxWidth(),
                                                backgroundColor = Color(0x33FAA37F),
                                                elevation = 0.dp
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(6.dp)
                                                ) {
                                                    Text(
                                                        route,
                                                        fontSize = 17.sp,
                                                        color = Color(0xFF666666),
                                                        modifier =Modifier.weight(1F)
                                                    )

                                                    Icon(
                                                        Icons.Filled.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color(0xFF666666),
                                                        modifier =Modifier.clickable {
                                                            user_tag_list2.remove(route)
//                                                            deleted_tag_list2.add(route)
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(15.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    try {
                                        Log.d("++++6666", "修改个人信息")
                                        val client = OkHttpClient()
                                        val urlBuilder = "http://124.221.166.194:80/api/v1/updateinfo".toHttpUrlOrNull()!!.newBuilder()

                                        val token = userviewModel.userInfoManager.token.firstOrNull().toString()
                                        val stringUtil = StringUtil()

                                        urlBuilder.addQueryParameter("allergen",stringUtil.append(user_tag_list2))
                                        urlBuilder.addQueryParameter("disease",stringUtil.append(user_tag_list))
                                        val url = urlBuilder.build()
                                        Log.d("++++6666", url.toString())

                                        var ddname = ""
                                        var ddgender = false
                                        var ddheight = 0f
                                        var ddnewweight = 0f
                                        var ddtarweight = 0f
                                        var ddtartime = 1

                                        if(textname.isNotEmpty()){
                                            ddname = textname
                                        }
                                        else{
                                            if (oldname != null) {
                                                ddname = oldname
                                            }
                                        }
                                        if(textgender.isNotEmpty()){
                                            if(textgender == "男"){
                                                ddgender = true
                                            }
                                        }
                                        else{
                                            ddgender = userviewModel.userInfo!!.usr_sex
                                        }
                                        if(textheight.isNotEmpty()){
                                            ddheight = textheight.toFloat()
                                        }
                                        else{
                                            if (oldheight != null) {
                                                ddheight = oldheight
                                            }
                                        }
                                        if(textnewweight.isNotEmpty()){
                                            ddnewweight = textnewweight.toFloat()
                                        }
                                        else{
                                            if (oldnewweight != null) {
                                                ddnewweight = oldnewweight
                                            }
                                        }
                                        if(texttarweight.isNotEmpty()){
                                            ddtarweight = texttarweight.toFloat()
                                        }
                                        else{
                                            if (oldtarweight != null) {
                                                ddtarweight = oldtarweight
                                            }
                                        }
                                        if(texttartime.isNotEmpty()){
                                            ddtartime = texttartime.toInt()
                                        }
                                        else{
                                            if (oldtartime != null) {
                                                ddtartime = oldtartime
                                            }
                                        }

                                        var gson = Gson();
                                        var usr = gson.toJson(
                                            User(
                                                usr_phone = "",
                                                usr_password = "" ,
                                                usr_name = ddname ,
                                                usr_sex = ddgender,
                                                usr_height = ddheight,
                                                new_weight = ddnewweight ,
                                                usr_age = 0 ,// ?????????????????
                                                tar_weight = ddtarweight,
                                                tar_time = ddtartime,
                                                avatar_url = "",
                                                usr_weight = 1.0f,// ?????????????????
                                                usr_email = "",
                                                usr_id = 0
                                            )
                                        )
                                        val body = RequestBody.create("application/json".toMediaTypeOrNull(), usr)
                                        Log.d("++++6666", body.toString())
                                        val request = Request.Builder()
                                            .url(url)
                                            .post(body)
                                            .header("token",token)
                                            .build()
                                        val response = client.newCall(request).execute()
                                        val jsonDataString = response.body?.string()
                                        Log.d("++++6666", jsonDataString.toString())
                                        val res = JSONObject(jsonDataString).getJSONArray("data")

                                        val user = gson.fromJson(res.getJSONObject(0).toString(), User::class.java)      //取第一个Map

                                        val allergens = stringUtil.split(res.getJSONObject(1).get("allergen").toString())  //一会需要转换成List<String>
                                        val diseases = stringUtil.split(res.getJSONObject(2).get("disease").toString())
                                        //  需要一个什么东西记录过敏源和疾病
                                        Log.d("++++6666", allergens.size.toString())
                                        Log.d("++++6666", diseases.size.toString())
                                        userviewModel.allergens.clear()
                                        userviewModel.diseases.clear()
                                        allergens.forEach{
                                            userviewModel.allergens.add(it)
                                        }
                                        diseases.forEach{
                                            userviewModel.diseases.add(it)
                                        }
                                        Log.d("++++6666", allergens.toString())
                                        Log.d("++++6666", diseases.toString())
                                        Log.d("++++6666", userviewModel.allergens.size.toString())
                                        Log.d("++++6666", userviewModel.diseases.size.toString())

                                        userviewModel.userInfo!!.usr_name = user.usr_name
                                        userviewModel.userInfo!!.usr_sex = user.usr_sex
                                        userviewModel.userInfo!!.usr_height = user.usr_height
                                        userviewModel.userInfo!!.new_weight = user.new_weight
                                        userviewModel.userInfo!!.tar_weight = user.tar_weight
                                        userviewModel.userInfo!!.tar_time = user.tar_time

                                        oldname = user.usr_name
                                        if(user.usr_sex == true){
                                            oldgender = "男"
                                        }
                                        else{
                                            oldgender = "女"
                                        }
                                        oldheight = user.usr_height
                                        oldnewweight = user.new_weight
                                        oldtarweight = user.tar_weight
                                        oldtartime = user.tar_time

                                        textname = ""
                                        textgender = ""
                                        textheight = ""
                                        textnewweight = ""
                                        texttarweight = ""
                                        texttartime = ""
                                        change_acc = true

                                        Log.d("++++fuck", res.toString())
                                    } catch (e: Exception) {
                                        Log.d("====777", e.toString())
                                        Log.d("====777", "set net_bad true")
                                        net_bad = true
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFAA37F),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        if(net_bad == true) {
                            Log.d("====777", "set net_bad false back")
                            net_bad = false
                            Toast.makeText(LocalContext.current, "网络连接错误", Toast.LENGTH_SHORT).show()
                        }
                        if(change_acc == true){
                            change_acc = false
                            Toast.makeText(LocalContext.current, "修改成功！", Toast.LENGTH_SHORT).show()
                        }
                        Text(
                            text = "保存个人信息",
                            fontSize = 19.sp,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.size(15.dp)) }
        }
    }
}