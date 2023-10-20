package com.example.daydiet.viewmodel

import android.app.TaskStackBuilder.create
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daydiet.model.UserInfoManager
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.model.entity.RequestEntity
import com.example.daydiet.model.entity.User
import com.example.daydiet.model.entity.UserEntity
import com.example.daydiet.service.Homeservice
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject

class UserViewModel(context: Context): ViewModel() {

    // 账号关联申请
    var request_list = mutableStateListOf<RequestEntity>()
        private set
    // 已关联账号
    var connected_list = mutableStateListOf<User>()
        private set
    // 登录账号密码
    var account by mutableStateOf("")
    var password by mutableStateOf("")

    var allergens = mutableStateListOf<String>()

    var diseases = mutableStateListOf<String>()

    var weightlist = mutableStateListOf<Float>()

    var weeks = mutableStateListOf<String>()

    var todayeat = mutableStateOf(0f)

    var islogin by mutableStateOf(false)

    var token = ""

    var userInfo: User ?= null
        private set

    var user_settings = mutableStateListOf<Float>()

    public val userInfoManager = UserInfoManager(context)

    var net_ok by mutableStateOf(true)
        private set
    fun setnet_ok (value: Boolean){
        net_ok = value
    }
    // 账号密码错误
    var accounterror by mutableStateOf(false)
        private set
    fun setaccounterror (value: Boolean){
        accounterror = value
    }
    // 登录成功
    var loginacc by mutableStateOf(true)
        private set
    fun setloginacc (value: Boolean){
        loginacc = value
    }
    // 注册成功
//    var registeracc by mutableStateOf(false)
//        private set
//    fun setregisteracc (value: Boolean){
//        registeracc = value
//    }
    // NavHostApp 初始化的时候读取本地数据
    init {
//        val scope= rememberCoroutineScope()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    // 向后端发token 后端判断有没有过期
                    val client = OkHttpClient()
                    val urlBuilder = "http://124.221.166.194:80/api/v1/checktoken".toHttpUrlOrNull()!!.newBuilder()
                    val url = urlBuilder.build()
                    var token = userInfoManager.token.firstOrNull().toString()
                    Log.d("++++fuck", token)
                    val builder = FormBody.Builder()
                    val formBody = builder.build()
                    val request = Request.Builder()
                        .url(url)
                        .post(formBody)
                        .header("token",token)
                        .build()

                    val response = client.newCall(request).execute()
                    val jsonDataString = response.body?.string()
//                    Log.d("++++test", response.toString())
                    val res = JSONObject(jsonDataString).get("code").toString()
                    Log.d("++++test", res)
                    // token过期
                    if(res!="200"){
                        islogin = false
                        userInfoManager.exit()
                    }
                    // token没过期
                    else{
                        var token = userInfoManager.token.firstOrNull().toString()
                        // 网络获取用户信息
                        val usr = JSONObject(jsonDataString).getJSONObject("data").toString()
                        Log.d("++++test", usr)
                        userInfo = Gson().fromJson(usr, User::class.java)
                        this@UserViewModel.token = userInfoManager.token.toString()

                         //找六个推荐相关的参数
                        Log.d("++++6666", "获取推荐参数init()")
                        val client = OkHttpClient()
                        val urlBuilder = "http://124.221.166.194:80/api/v1/recommend/get_settings".toHttpUrlOrNull()!!.newBuilder()

                        val url = urlBuilder.build()
                        val builder = FormBody.Builder()
                        val formBody = builder.build()

                        Log.d("++++6666", token)
                        val request = Request.Builder()
                            .url(url)
                            .post(formBody)
                            .header("token",token)
                            .build()

                        val response = client.newCall(request).execute()
                        val jsonDataString = response.body?.string()

                        Log.d("++++666", jsonDataString.toString())
                        val res = JSONObject(jsonDataString).getJSONObject("data")
                        Log.d("++++666", res.toString())
                        var settings = JSONObject(res.get("settings").toString())
                        //  下边设置几个推荐参数
                        user_settings.clear()
                        user_settings.add(settings.get("number").toString().toFloat())
                        user_settings.add(settings.get("shicaiqihe").toString().toFloat())
                        user_settings.add(settings.get("yingyangjiegou").toString().toFloat())
                        user_settings.add(settings.get("teshuxuqiu").toString().toFloat())
                        user_settings.add(settings.get("jijieshiling").toString().toFloat())
                        user_settings.add(settings.get("yinshipianhao").toString().toFloat())

                        // 获取 用户的过敏源和疾病

                        Log.d("++++666", res.get("settings").toString())
                        Log.d("++++666", res.get("allergens").toString())
                        Log.d("++++666", res.get("diseases").toString())

                        val allergensData = res.optJSONArray("allergens")
                        allergens.clear()
                        for (i in 0 until allergensData.length()) {
                            allergens.add(allergensData.getString(i))
                        }

                        val diseasesData = res.optJSONArray("diseases")
                        diseases.clear()
                        for (i in 0 until diseasesData.length()) {
                            diseases.add(diseasesData.getString(i))
                        }
                        Log.d("++++666", allergens.size.toString())
                        Log.d("++++666", diseases.size.toString())
                        islogin = true

                    }
                    net_ok = true
                }catch (e: Exception){
                    Log.d("+++666", "cannot access net")
                    net_ok = false
//                    Toast.makeText(context, "服务器发生错误", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 是否已登录
    val logged: Boolean
        get() {
            return userInfo != null
//            return user != null
        }

    // 网络请求登录TODO********************************
    suspend fun login(){
        try {
            // 网络请求数据回传
            Log.d("++++666", "用户登录")
            val client = OkHttpClient()
            val urlBuilder = "http://124.221.166.194:80/api/v1/visitorlogin".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addQueryParameter("user_phone",account)
//            urlBuilder.addQueryParameter("user_password",password)
            val url = urlBuilder.build()
            Log.d("++++logintest++++", url.toString())
            val builder = FormBody.Builder()
            val formBody = builder.build()
            val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()
            val jsonDataString = response.body?.string()
            Log.d("++++logintest++++", jsonDataString.toString())
            val code = JSONObject(jsonDataString).get("code").toString()
            if(code != "200") {
                Log.d("++++logintest++++", "pass or account error")
                accounterror = true
                net_ok = true
                islogin = false
            }
            else {
                Log.d("++++logintest++++", "code is not 201")
                Log.d("++++logintest++++", code)
                val res = JSONObject(jsonDataString).getJSONObject("data").toString()
                val gson = Gson()
                val type = object : TypeToken<User>(){}.type
                val data:User = Gson().fromJson(res,type)
                Log.d("++++logintest++++", res.toString())
                Log.d("++++logintest++++", data.toString())
                userInfo = data
                token = data.usr_phone
                Log.d("++++logintest++++", account)
                Log.d("++++logintest++++", password)
                Log.d("++++logintest++++", jsonDataString.toString())
//                viewModelScope.launch {
                    Log.d("++++logintest++++", "++++logintest++++")
                    userInfoManager.save(userInfo!!.usr_name, userInfo!!.usr_phone)
                    userInfoManager.token.firstOrNull()?.let { Log.d("++++logintest++++", it) }
                    userInfoManager.userName.firstOrNull()?.let { Log.d("++++logintest++++", it) }
//                }
                accounterror = false
                net_ok = true
                loginacc = false

                // 找六个推荐相关的参数
                Log.d("++++6666", "获取推荐参数login()")
                val client = OkHttpClient()
                val urlBuilder = "http://124.221.166.194:80/api/v1/recommend/get_settings".toHttpUrlOrNull()!!.newBuilder()
                val url = urlBuilder.build()
                val builder = FormBody.Builder()
                val formBody = builder.build()
                var token = data.usr_phone
                Log.d("++++666", token.toString())
                val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .header("token",token)
                    .build()
                val response = client.newCall(request).execute()
                val jsonDataString = response.body?.string()
                Log.d("++++666", jsonDataString.toString())

                var info = JSONObject(jsonDataString).getJSONObject("data")
                Log.d("++++666", info.toString())
                var settings = JSONObject(info.get("settings").toString())
                //  下边设置几个推荐参数
                user_settings.clear()
                user_settings.add(settings.get("number").toString().toFloat())
                user_settings.add(settings.get("shicaiqihe").toString().toFloat())
                user_settings.add(settings.get("yingyangjiegou").toString().toFloat())
                user_settings.add(settings.get("teshuxuqiu").toString().toFloat())
                user_settings.add(settings.get("jijieshiling").toString().toFloat())
                user_settings.add(settings.get("yinshipianhao").toString().toFloat())
                // 获取 用户的过敏源和疾病
                Log.d("++++666", info.get("settings").toString())
                Log.d("++++666", info.get("allergens").toString())
                Log.d("++++666", info.get("diseases").toString())
                val allergensData = info.optJSONArray("allergens")
                allergens.clear()
                for (i in 0 until allergensData.length()) {
                    allergens.add(allergensData.getString(i))
                }
                val diseasesData = info.optJSONArray("diseases")
                diseases.clear()
                for (i in 0 until diseasesData.length()) {
                    diseases.add(diseasesData.getString(i))
                }
                Log.d("++++666", allergens.size.toString())
                Log.d("++++666", diseases.size.toString())
                islogin = true
            }
        } catch (e: Exception) {
            accounterror = false
            net_ok = false
        }
        Log.d("++++998net", net_ok.toString())
        Log.d("++++998pass", accounterror.toString())
    }

    fun exitlogin(){
        userInfo = null
        viewModelScope.launch {
            userInfoManager.exit()
        }
    }
}