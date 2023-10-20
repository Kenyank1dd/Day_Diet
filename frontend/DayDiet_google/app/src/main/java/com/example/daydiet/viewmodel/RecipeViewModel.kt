package com.example.daydiet.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.Pyqmessage
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.model.entity.SimpleString
import com.example.daydiet.service.Common
import com.example.daydiet.service.Common.Base_Url
import com.example.daydiet.service.Homeservice
import com.google.gson.Gson
import okhttp3.MediaType
import org.w3c.dom.Text

class RecipeViewModel: ViewModel() {
    // 食谱列表
    var list = mutableStateListOf<RecipeEntity>()
        private set
    var list1 = mutableStateListOf<RecipeEntity>()
        private set
    var list2 = mutableStateListOf<RecipeEntity>()
        private set
    var list_index = mutableStateOf(0)

    var score_list = mutableStateListOf<Float>()
        private set
    var score_list1 = mutableStateListOf<Float>()
        private set
    var score_list2 = mutableStateListOf<Float>()
        private set

    var reason = mutableStateListOf<String>()
        private set
    var reason1 = mutableStateListOf<String>()
        private set
    var reason2 = mutableStateListOf<String>()
        private set

    // 服务器是否正常
    var net_bad = mutableStateOf(false)
        private set

    // 创新生成
    var recipename = mutableStateOf("")

    var createurl = mutableStateOf("")

    // 黑暗料理
    var darkrecipe = mutableStateOf("")
        private set

    fun updatedark(text: String){
        darkrecipe.value = text
    }
    var material = mutableStateListOf<String>()
    var step = mutableStateListOf<String>()
    var tips = mutableStateListOf<String>()

    // 黑暗料理输入
    var text_gongyi = mutableStateOf("")
        private set
    var text_nandu = mutableStateOf("")
        private set
    var text_weidao = mutableStateOf("")
        private set
    var ings = mutableStateListOf<String>()

    // 食谱详情
    var recipedetail = mutableStateOf(RecipeEntity())

    // 快速记录识别结果
//    var name = mutableStateOf("")
//    var calorie = mutableStateOf("")
    var filepath = mutableStateOf("")

    // ***************
    fun initial_recipe(){
        recipedetail.value = RecipeEntity(
            1,"test_recipe","https://img-nos.yiyouliao.com/alph/361f865ab039eb4358de068aa05ca3d3.jpeg?yiyouliao_channel=msn_image",
            "test_rec_prac","test_rec_taste","test_rec_time","test_rec_diff",
            "test_rec_main","test_rec_excipient","test_rec_step","test_rec_less","test_rec_more",
            "test_rec_no","11","22","33"
        )
    }


    fun findDetail(selected: Int) {
        Log.d("++++www3", selected.toString())
        Log.d("++++www3", list.size.toString())
        if(list_index.value == 0){
            list.forEach {
                Log.d("++++www4", it.rec_id.toString() + ":" + selected.toString())
                if(it.rec_id == selected.toLong()){
                    recipedetail.value = it
                }
            }
        }
        else if(list_index.value == 1){
            list1.forEach {
                Log.d("++++www4", it.rec_id.toString() + ":" + selected.toString())
                if(it.rec_id == selected.toLong()){
                    recipedetail.value = it
                }
            }
        }
        else if(list_index.value == 2){
            list2.forEach {
                Log.d("++++www4", it.rec_id.toString() + ":" + selected.toString())
                if(it.rec_id == selected.toLong()){
                    recipedetail.value = it
                }
            }
        }

        Log.d("++++www5", recipedetail.toString())
    }
}