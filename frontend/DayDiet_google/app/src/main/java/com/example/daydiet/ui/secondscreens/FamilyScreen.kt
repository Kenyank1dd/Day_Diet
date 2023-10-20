package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.model.Domain.FamilyInfo
import com.example.daydiet.model.entity.FamilyEntity
import com.example.daydiet.service.StringUtil
import com.example.daydiet.ui.components.ArticalItem
import com.example.daydiet.ui.components.FamilyItem
import com.example.daydiet.ui.components.PyqItem
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.FamilyViewModel
import com.google.accompanist.insets.statusBarsPadding
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
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun FamilyScreen(
    familyviewModel: FamilyViewModel = viewModel(),
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    var buttontext by remember {
        mutableStateOf("关联我的家人")
    }

    val user = LocalUserViewModel.current

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        scope.launch {
            withContext(Dispatchers.IO){

                Log.d("++++666", "查询家庭成员信息")
                val client = OkHttpClient()
                val urlBuilder = "http://124.221.166.194:80/api/v1/get_dis_all".toHttpUrlOrNull()!!.newBuilder()

                val url = urlBuilder.build()

                val builder = FormBody.Builder()
                val formBody = builder.build()

                val token = user.userInfoManager.token.firstOrNull().toString()
                val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .header("token",token)
                    .build()
                Log.d("++++666", "yes")
                val response = client.newCall(request).execute()
                val jsonDataString = response.body?.string()

                Log.d("++++666", jsonDataString.toString())
                val data = JSONObject(jsonDataString).getJSONArray("data")     //所有信息
                Log.d("++++666", data.toString())
                var  stringUtil = StringUtil()
                familyviewModel.list.clear()
                for(i in 0 until data.length()){
                    var jsonObject = data.getJSONObject(i);
                    var allergens = stringUtil.split(jsonObject.get("allergen").toString()) as ArrayList<String>
                    var diseases = stringUtil.split(jsonObject.get("disease").toString()) as ArrayList<String>
                    var relation = jsonObject.get("relation").toString()
                    var height = jsonObject.get("height").toString().toFloat()
                    var weight = jsonObject.get("weight").toString().toFloat()
                    var age = jsonObject.get("age").toString().toLong()
                    var BMI = jsonObject.get("BMI").toString().toFloat()
                    //四舍五入
                    BMI = BigDecimal(BMI.toDouble()).setScale(2, RoundingMode.HALF_UP).toFloat()
                    var base = jsonObject.get("base").toString().toFloat()   //基础代谢
                    var familyinfo = FamilyEntity(allergens,diseases,relation,height,weight, age, BMI, base)
                    Log.d("++++666", familyinfo.toString())
                    familyviewModel.list.add(familyinfo)         //信息存储在FamilyEntity
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
                        text = "家庭成员",
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
                text = {
                    Text(
                        text = buttontext,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                },
                onClick = {
                    navHostController.navigate(Destinations.InvestScreen.route)
                },
                backgroundColor = Color(0xFFFFB47F),
                modifier = Modifier.padding(bottom = 30.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            items(familyviewModel.list){ family->
                Spacer(modifier = Modifier.size(8.dp))
                FamilyItem(family)
                Spacer(modifier = Modifier.size(4.dp))
            }
            item {
                Spacer(modifier = Modifier.size(100.dp))
            }
        }
    }
}