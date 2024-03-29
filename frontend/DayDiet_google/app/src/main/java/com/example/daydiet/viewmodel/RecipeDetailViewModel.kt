package com.example.daydiet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.RecipeEntity

class RecipeDetailViewModel: ViewModel(){
    val RecipeDetail = RecipeEntity(
        1111111,
        "蓑衣黄瓜",
        "https://docs.bughub.icu/compose/assets/banner1.webp",
        "拌",
        "酸辣味",
        "10分钟",
        "初级入门",
        "黄瓜1根;",
        "油适量;盐1克;大蒜2瓣;干辣椒2个;香葱1根;味极鲜酱油15克;陈醋3克;糖2克;",
        "步骤1:\n备好所需食材;\n" +
                "步骤2:\n黄瓜洗净后放在熟食案板上，两边各放一根筷子（如图，这样可以避免把黄瓜切断）;\n" +
                "步骤3:\n刀与黄瓜成直角，均匀切成2mm左右连刀片（注意，黄瓜头比较细，筷子不够高，切的时候要悠着点，别切断了）;\n" +
                "步骤4:\n一面直角刀切好后，再翻面切斜刀，大约45度角;\n" +
                "步骤5:\n切完后就可以随意拉伸哈，然后放到大碗里，把盐均匀撒在黄瓜上，腌制1小时左右;\n" +
                "步骤6:\n在腌制黄瓜期间，把蒜瓣切小粒，香葱切成葱花，辣椒去籽，剪成小条;\n" +
                "步骤7:\n小碗里放入辣椒条和葱花;\n" +
                "步骤8:\n锅中倒入适量油（我用的是菜籽油），开中火烧至冒烟后关火;\n" +
                "步骤9:\n将热油淋入辣椒和葱花上炸出香味（油没用完，不用太多）;\n" +
                "步骤10:\n然后调入蒜末、味极鲜、醋和糖，搅拌均匀备用;\n" +
                "步骤11:\n将腌出来的黄瓜水倒掉，把黄瓜放入碗中;步骤12:最后将调料汁均匀浇到蓑衣黄瓜上就可以开吃啦;",
        "儿童;",
        "高血压人群;高血脂人群;减肥人群;高血糖人群;",
        "无",
        "43.0",
        "2.4",
        "3.3"
    )
}