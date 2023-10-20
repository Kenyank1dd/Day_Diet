package com.example.daydiet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.Pyqmessage
import com.example.daydiet.service.Homeservice

class PyqViewModel:ViewModel() {

    // 朋友圈列表数据
    var list = listOf(
        Pyqmessage(
            "悦食记",
            "轻点APP，智慧无处不在！\n一键扫描，服务触手可及！\n妈妈再也不用担心我的饮食！",
            "2023-04-15",
            "https://img.zcool.cn/community/01496e5dd64767a8012129e27d8847.jpg"
        ),
        Pyqmessage(
            "家庭煮夫",
            "今天是来到Day Diet的第21天\n终于每天都不需要纠结吃什么了\n真是选择困难症的福音",
            "2023-04-13",
            "https://img.zcool.cn/community/0189d85c3c421aa80121fbb0284f6c.jpg@1280w_1l_2o_100sh.jpg"
        ),
        Pyqmessage(
            "小飞棍来咯",
            "合理饮食搭配\n减重不减营养\n健康！",
            "2023-04-12",
            "https://img.zcool.cn/community/01277d5b30b2e0a80121b994f7e3e6.jpg@1280w_1l_2o_100sh.jpg"
        ),
        Pyqmessage(
            "榴南",
            "小孩子才做选择\n健康和好身材\n我全都要！",
            "2023-04-08",
            "https://img.sandingtv.com/20181017/3f54ed6432ea8cf7b48e74b834108de4.jpg"
        ),
        Pyqmessage(
            "在人海里游",
            "选对方法\n合理饮食\n一切都很简单",
            "2023-04-06",
            "https://zhongces3.sina.com.cn/products/202009/d8653f91e3f8ab2586bb03fc95c7e92d.jpeg"
        ),
        Pyqmessage(
            "惊欢",
            "今天晚餐后的水果甜品\n刚烤出来的华夫饼真的香浓\n开心",
            "2023-04-15",
            "https://tse4-mm.cn.bing.net/th/id/OIP-C.j9Y0fZ3oKQP0nataK5XhTQHaJ4?pid=ImgDet&rs=1"
        )
    )
        private set

    val homeservice = Homeservice.instance()
    suspend fun pyqmessageData(){

        val pyqmessageRes = homeservice.pyqmessage("wangshuang")
        Log.d("++++fuck", pyqmessageRes.data.toString())
        if(pyqmessageRes.code == 0){
            list = pyqmessageRes.data
            print(pyqmessageRes.data)
        }else{
            //不成功的情况
            val message = pyqmessageRes.message
        }
    }
}