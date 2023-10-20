package com.example.daydiet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.daydiet.R
import com.example.daydiet.model.entity.SolarTermEntity

class SolarTermViewModel: ViewModel() {
    // 夏至
    // 小暑
    // 大暑
    // 立秋 8.8    02:22:22
    // 处暑 8.23   17:00:41
    // 白露 9.8    05:26:10
    // 秋分 9.23   14:49:22
    // 寒露 10.8   21:15:08
    // 霜降 10.24  00:20:27
    val solar_term_list = listOf(
        SolarTermEntity("夏至",
            "2023-06-20","6月21日-7月6日",
            R.drawable.xiazhi,
            "阳气最盛，护阳气，养脾胃","荷叶：清凉解暑，止渴生津",
            "吃苦尝酸，解热祛暑","茯苓：利水渗湿，健脾"),
        SolarTermEntity("小暑",
            "2023-07-06","7月7日-7月22日",
            R.drawable.xiaoshu,
            "小暑黄鳝赛人参，补气强筋","鸭肉：滋阴补虚，养胃利水",
            "祛湿养气，健脾宁心","小白菜：解热除烦，通肠胃"),
        SolarTermEntity("大暑",
            "2023-07-22","7月23日-8月7日",
            R.drawable.dashu,
            "治冬病，防暑热","玉米：健脾开胃，防癌健脑",
            "清淡酸甘，补气降火","大暑老鸭胜补药"),
        SolarTermEntity("立秋",
            "2023-08-07","8月8日-8月22日",
            R.drawable.liqiu,
            "调精神，敛肺气，除秋燥","莲藕：清热滋阴，开胃养血",
            "滋阴润燥，增酸强肝","银耳：滋补生津，润肺养胃"),
        SolarTermEntity("处暑",
            "2023-08-22","8月23日-9月7日",
            R.drawable.chushu,
            "生津润燥，清热安神","防秋燥，促睡眠",
            "处暑食俗吃鸭子，贴膘补身","甘蔗：和中润燥，清热除烦"),
        SolarTermEntity("白露",
            "2023-09-07","9月8日-9月22日",
            R.drawable.bailu,
            "补阴气，防秋寒，生津润肺","白露必饮白露茶",
            "减苦增辛，助筋补血","乌鸡：延缓衰老，强筋健骨"),
        SolarTermEntity("秋分",
            "2023-09-22","9月23日-10月07日",
            R.drawable.qiufen,
            "调阴阳，护脾胃","秋分拜神食秋菜",
            "收神养心，阴平阳秘","桂圆：补气血，安神志"),
        SolarTermEntity("寒露",
            "2023-10-07","10月08日-10月23日",
            R.drawable.hanlu,
            "防燥邪，暖肺脾","吃花糕，饮菊花酒",
            "养阴防燥，润肺益胃","枸杞子：养肝滋肾又润肺"),
        SolarTermEntity("霜降",
            "2023-10-23","10月24日-11月7日",
            R.drawable.shuangjiang,
            "固肾气，补肺气","霜降吃丁柿，不会流鼻涕",
            "健脾养阴，固肾补肺","鲢鱼：健脾补气，温中暖胃"),
    )
}