package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.model.entity.ChatEntity
import com.example.daydiet.service.ChatUtil
import com.example.daydiet.viewmodel.ChatViewModel
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
fun RobotScreen(
    navHostController: NavHostController,
    onBack: ()->Unit,
    chatViewModel: ChatViewModel = viewModel()
) {
    val scope= rememberCoroutineScope()    //协程

    var chat_list = mutableStateListOf<ChatEntity>()

    var change_flag by remember {
        mutableStateOf(false)
    }

    var inputText by remember { mutableStateOf("") }

    chat_list.add(ChatEntity(0,"你好，我是小悦，很乐意为您效劳~"))
    chat_list.add(ChatEntity(0,"请问有什么可以帮助您？"))

    chatViewModel.list.clear()
    chatViewModel.list.add(ChatEntity(0,"你好，我是小悦，很乐意为您效劳~"))
    chatViewModel.list.add(ChatEntity(0,"请问有什么可以帮助您？"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
    ) {
        // 标题栏
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                Text(
                    text = "健康咨询",
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
        Spacer(modifier = Modifier.height(5.dp))

//        ChatList(chat_list,Modifier.weight(1f))

        Column(modifier = Modifier.weight(1f)) {
            Content{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .weight(1f)
                ){
                    items(chat_list){ message->
                        if (message.who.equals(1)){
                            // 用户消息
                            UserMessage(message.chat_content)
                        }else{
                            // 机器人消息
                            RobotMessage(message.chat_content)
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier
            .height(330.dp)
            .background(Color(0xFFE4E4E4))
        ){
            Content{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE4E4E4))
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.keyboard),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    BasicTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .background(Color.White)
                            .weight(1f)
                            .height(40.dp)
                            .padding(start = 5.dp, end = 5.dp, top = 10.dp),
                        textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 14.sp),
                        keyboardActions = KeyboardActions (
                            onSend = {
                                if(inputText != ""){
                                    chat_list.add(ChatEntity(0, inputText))
                                    inputText = ""
                                    // TODO 获取回复并添加至chat_list
                                    Log.d("++++", "message has been added to chat_list")
                                }
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Send
                        )
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = {
                            if(inputText != ""){
                                chat_list.add(ChatEntity(1, inputText))
                                change_flag = !change_flag
                                chatViewModel.list.add(ChatEntity(1, inputText))
                                inputText = ""
                                // TODO 获取回复并添加至chat_list
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        try {
                                            var len = chatViewModel.list.size
                                            var num = (len-3)/2
                                            Log.d("++++666", len.toString())
                                            Log.d("++++666", num.toString())
                                            Log.d("++++666", chatViewModel.list.get(0).chat_content.toString())
                                            var input = chatViewModel.list.get(len-1).chat_content
                                            var history = ArrayList<List<String>>()
                                            for( i in 0 until num){
                                                var index1 = 2*i+2
                                                var index2 = 2*i+3
                                                var temp = ArrayList<String>()
                                                var ask = chatViewModel.list.get(index1)
                                                var ans = chatViewModel.list.get(index2)
                                                temp.add(ask.chat_content)
                                                temp.add(ans.chat_content)
                                                history.add(temp)
                                            }
                                            Log.d("++++666", input)
                                            Log.d("++++666", history.toString())
                                            var chatUtil = ChatUtil()
                                            var reply = chatUtil.chat(input,history)
                                            Log.d("++++666", reply)

                                            chat_list.add(ChatEntity(0, reply))
                                            change_flag = !change_flag
                                            chatViewModel.list.add(ChatEntity(0, reply))

                                        } catch (e: Exception) {
                                            Log.d("++++666", e.toString())
                                        }
                                    }
                                }

                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00C864),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text("发送", fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "● Tips:",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 15.dp)
            )
            Text(
                text = "智能健康顾问（小悦）为您提供健康咨询服务，可以给出健康问题的答复和相关建议。",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 30.dp, top = 5.dp, bottom = 8.dp)
            )
            Text(
                text = "您可以这样向她提问：",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 30.dp, top = 5.dp, bottom = 8.dp)
            )
            Text(
                text = "- 请问空腹喝酸奶对身体有害吗？",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 45.dp, top = 5.dp, bottom = 8.dp)
            )
            Text(
                text = "- 请问每天必须吃水果吗？",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 45.dp, top = 5.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun Content(content: @Composable () -> Unit = {}) {
    content()
}

@Composable
fun ChatList(
    chat_list: List<ChatEntity>,
    modifier: Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ){
        items(chat_list){
            if (it.who.equals(1)){
                // 用户消息
                UserMessage(it.chat_content)
            }else{
                // 机器人消息
                RobotMessage(it.chat_content)
            }
        }
    }
}

@Composable
fun UserMessage(chat_content: String){
    Row(
        modifier = Modifier
            .padding(start = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            useText(
                text = chat_content,
                color = Color.Black,
                modifier = Modifier.meBackground(Color.Green)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.robot2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun RobotMessage(chat_content: String){
    Row(
        modifier = Modifier
            .padding(end = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.robot),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp)))
        useText(
            text = chat_content,
            color = Color.Black,
            modifier = Modifier.otherBackground(Color.White)
        )
    }
}

@Composable
fun useText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 15,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign =TextAlign.Start
){
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier,
        textAlign = textAlign
    )
}

fun Modifier.meBackground(color: Color):Modifier = this
    .drawBehind {
        val bubble = Path().apply {
            val rect = RoundRect(
                10.dp.toPx(),
                0f,
                size.width - 10.dp.toPx(),
                size.height,
                4.dp.toPx(),
                4.dp.toPx()
            )
            addRoundRect(rect)
            moveTo(size.width - 10.dp.toPx(), 15.dp.toPx())
            lineTo(size.width - 5.dp.toPx(), 20.dp.toPx())
            lineTo(size.width - 10.dp.toPx(), 25.dp.toPx())
            close()
        }
        drawPath(bubble, color)
    }
    .padding(20.dp, 10.dp)

fun Modifier.otherBackground(color: Color):Modifier = this
    .drawBehind {
        val bubble = Path().apply {
            val rect = RoundRect(
                10.dp.toPx(),
                0f,
                size.width - 10.dp.toPx(),
                size.height,
                4.dp.toPx(),
                4.dp.toPx()
            )
            addRoundRect(rect)
            moveTo(10.dp.toPx(), 15.dp.toPx())
            lineTo(5.dp.toPx(), 20.dp.toPx())
            lineTo(10.dp.toPx(), 25.dp.toPx())
            close()
        }
        drawPath(bubble, color)
    }
    .padding(20.dp, 10.dp)

@Composable
fun BottomInputBar(
    chatViewModel: ChatViewModel,
    onInputListener:(ChatEntity)->Unit,
){


}
