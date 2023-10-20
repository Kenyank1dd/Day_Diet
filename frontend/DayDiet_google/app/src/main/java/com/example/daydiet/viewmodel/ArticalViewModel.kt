package com.example.daydiet.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.daydiet.model.entity.ArticalEntity

class ArticalViewModel:ViewModel() {

    val swiperData = listOf(
        ArticalEntity(
            6,
            "这才是正宗的家常菜！番茄炖牛腩，暴露了家庭厨艺的真实实力",
            "2023-04-04",
            "猪猪之家",
            "https://img-nos.yiyouliao.com/alph/361f865ab039eb4358de068aa05ca3d3.jpeg?yiyouliao_channel=msn_image",
            "<div class=\"g-main\">\n" +
                    "    <!--正文标题 start-->\n" +
                    "    <div class=\"m-title-box\">\n" +
                    "        <h1 class=\"u-title\">\n" +
                    "            炒土豆丝极简史\n" +
                    "        </h1>\n" +
                    "\n" +
                    "    </div>\n" +
                    "    <!--正文标题 end-->\n" +
                    "    <!--左侧栏 start-->\n" +
                    "    <div class=\"m-l-main\">\n" +
                    "        <!--文章正文 start-->\n" +
                    "        <div id=\"articleBox\" class=\"con-text\">\n" +
                    "            <div id=\"article_inbox\">\n" +
                    "                <div class=\"m-player_box\" id=\"video\">\n" +
                    "                    <div id=\"a1\"></div>\n" +
                    "                </div>\n" +
                    "                <div id=\"MultiAttachPh\" style=\"display: none\">\n" +
                    "                    \n" +
                    "                </div>\n" +
                    "                <div id=\"PicUrlPh\" style=\"display: none\">\n" +
                    "                    <img src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c1593182f.jpg\" border=\"0\">\n" +
                    "                </div>\n" +
                    "                <div class=\"u-mainText\">\n" +
                    "<p>　　土豆丝，以马铃薯切丝炒制，是一道家常菜。这道菜，家常到不能再家常，但是它的味道，却能在一个“家常”名下有诸多变化。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871498\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c1593182c.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　单单是调味一项就有百搭的风范，可以放盐、放糖、放醋、放辣等等，至于配料同样可以搭青椒搭肉丝搭鸡蛋等等。可谓丰俭由人，味道多样。</p>\n" +
                    "<p>　　<strong>这是一道千家万户餐桌上的家常菜，咱们今天就来溯源一下，它是怎么出现的。</strong></p>\n" +
                    "<p>　　<strong>“切丝”不简单</strong></p>\n" +
                    "<p>　　<strong>炒土豆丝，主力是炒，要义却在于“丝”，没有“丝”，这道菜的口感和味道就无从谈起。</strong></p>\n" +
                    "<p>　　而切丝，考究的是<strong>刀工</strong>，现在的厨师仍旧有很多人通过切土豆丝来磨练刀工。对着土豆这一刀刀切下去，展现出来的，是中华烹饪传奇数千年发展史的一个个华丽丽的切面。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871499\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c1593182d.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　成书于1600年前南北朝时期的《齐民要术》一书中，就提到了不少当时的刀工技术，其中关于切丝这一项就出现了细切、缕切、细缕切等多种提法，这些不同的切丝法对应的就是不同的切丝外形。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871500\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c1593182e.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　虽然那个时候的土豆，还岁月静好地生长在距离中土万里之遥的南美洲安第斯山上，但是这些中华刀工切法已经应用于蔬菜之上。</p>\n" +
                    "<p>　　《齐民要术》中就提到了蔬菜的菹（zū）法（也就是古人的腌菜方法），其中有不少蔬菜（比如笋、葱）都需要切丝加工。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871501\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c1593182f.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　也就是说，<strong>最迟到南北朝时期，炒土豆丝所必需的刀法已经备好。</strong>至于土豆丝所必须的步骤“炒”，这一时期也已经出现了（参见前作：《请珍惜西红柿炒鸡蛋》）。</p>\n" +
                    "<p>　　但是，这道土豆丝的主料土豆，还需要再等待1200年，才姗姗来华。</p>\n" +
                    "<p>　　<strong>漂洋过海的土豆</strong></p>\n" +
                    "<p>　　<strong>土豆传入中国是一件扑朔迷离的事情</strong>，学界推断其进入中国的时间，有人主张是17世纪，也有人主张是19世纪，至于土豆进入中国的路线也有东南沿海传入、外国进贡京畿、云贵边疆传入等等说法。</p>\n" +
                    "<p>　　之所以出现这种模糊，原因之一是在土豆进入中国之前，还有一种本土植物也叫做“土豆”，这就是药材<strong>黄独</strong>。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871502\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c15931830.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p class=\"pictext\" align=\"center\">　　黄独</p>\n" +
                    "<p>　　这导致在文献中出现“马铃薯”之前，“土豆”这个名字需要仔细推断辨认是否是今天我们所说的土豆，这个推断辨认的过程难免出现争议和误差。</p>\n" +
                    "<p>　　尽管如此，目前还是有不少学者认为，明代万历年间笔记中记载的宫廷里皇帝吃的珍味中出现过的“土豆”一词，指的就是现在的土豆。</p>\n" +
                    "<p>　　也就是说，<strong>在明朝万历年间，土豆是由上林苑嘉蔬署种植并作为稀奇的食物，贡献给了皇帝。</strong></p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871503\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c15931831.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　由于文献中没有记载供给万历皇帝的土豆被加工成了什么样子，因此我们无法确定这上贡的珍奇菜品是不是土豆丝。</p>\n" +
                    "<p>　　<strong>到了清代康熙年间，土豆开始在一些地方县志中出现了，这代表土豆种植开始从宫廷走向民间。</strong></p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871504\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c15931832.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　这个推广种植的过程是非常缓慢的，大约在公元1800年前后，也就是万历皇帝吃到土豆的200年之后，土豆才被大规模引入山西、四川等地。</p>\n" +
                    "<p>　　虽然推广种植的过程非常缓慢，但是另外一方面，<strong>土豆作为百分之百的洋血统舶来品，对比其他舶来品加入中华菜系的漫长时间，它的加入却是非常迅速的</strong>，而且是没有遇到什么阻力的。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871505\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c15931833.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　之所以老百姓可能需要很久才能知道土豆，却能很快就接受吃土豆，原因只有一个：<strong>在清代引进并扩大土豆种植的浪潮中，最大的动力或者说压力，来自于</strong><strong> 饥荒</strong><strong>的威胁。</strong></p>\n" +
                    "<p>　　在这一轮种植扩张中，土豆因为产量大、种植门槛不高，在很多地区是作为荒年甚至丰年的主粮出现的。对于贫民来说，这是救命的东西，连主食都不够吃，哪里会顾得上佐餐的土豆菜。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871506\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c15931834.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　因此，在这一时期的文献记录中，伴随土豆出现的最早的加工形态，或者是整个的用来蒸煮或者烧烤，或者是加工成土豆片、土豆粉，并没有土豆丝。</p>\n" +
                    "<p>　　正如清代严如熤的《三省边防备览》中所说，老百姓做土豆片、土豆粉，是用来做馍做饼当饭吃的。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871507\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c15931835.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　到了清末，土豆已经在全国范围内广泛种植，产量上来以后，主粮的需求得到了暂时性部分满足，土豆就向小吃、菜肴领域进化了，这种进化往往是在大城市先出现的。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871508\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c15931836.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　土豆丝是具体什么时候进化出来的，这个还没有实锤的证据，个人推断，大约是在清末，时间不会太早，但是其传播速度是非常快的。到了20世纪二三十年代，很多城市的饭馆中都出现了这道菜。</p>\n" +
                    "<p>　　<strong>土豆丝——家常味道</strong></p>\n" +
                    "<p>　　<strong>不过，这道土豆丝，因为土豆供应量大、价格低廉，而且炒制方便快捷，从出现伊始就被定位成家常菜，从来没有进入筵席的机会。</strong></p>\n" +
                    "<p>　　比较高级的炒土豆丝版本，大约是那个时候作家梁实秋吃的土豆丝，他喜欢去当时北平的“厚德福”饭庄吃饭，“厚德福”的一绝就是<strong> 瓦块鱼</strong>。</p>\n" +
                    "<p>　　这道菜，是精选鲤鱼鲢鱼的精华部分，用温油炸成微微卷曲犹如瓦片的鱼段，上面再浇上一层粘稠透明的糖醋汁，最后佐以姜末。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871509\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c15931837.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p class=\"pictext\" align=\"center\">　　瓦块鱼</p>\n" +
                    "<p>　　说的这么热闹，我们的主角土豆丝是作为收尾的配餐，在这一道菜的末尾才出现的——也就是一盘子瓦块鱼差不多快吃完的时候，伙计会过来建议：用剩下的瓦块鱼汁，给客人焙一点儿面。</p>\n" +
                    "<p>　　这用菜汁焙的面，犹如焦炒过一般，酥、脆、微带甜酸，比面条细、比面条脆，味道别致，它就是特来客串面条的土豆丝，是厨师把土豆擦丝，然后下油锅炒出来的。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871510\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c15931838.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　梁实秋记录的这种土豆丝，还属于特殊情况。<strong>一般情况下，土豆丝连这个名菜配餐的角色都担当不了，它在当时就已经是一道再寻常不过的家常菜。</strong></p>\n" +
                    "<p>　　20世纪三四十年代，文物专家王世襄就读于北平燕京大学，当时的燕京大学东门外有一家主营家常菜的饭馆，因掌柜姓常行三而被称为“常三”饭馆，“常三”的名菜就是软炸里脊、肉末炒松花蛋以及焦溜土豆丝等等。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871511\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/gif/site2/20180316/f44d305ea8c01c15931839.gif\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p class=\"pictext\" align=\"center\">　　<strong>土豆丝能出名，不是因为它稀罕，而是因为物美价廉，深受学生欢迎。</strong></p>\n" +
                    "<p>　　顺带说一句，王世襄自己的做菜手艺就非常好，汪曾祺就传言说，当年王世襄曾以一道绝活菜“闷葱”技压朋友圈，后来王世襄还担任过全国烹饪名师技术表演鉴定会的特邀顾问。</p>\n" +
                    "<p>　　而几十年前学生时代的一道焦溜土豆丝能让他念念不忘，大约是因为漫长岁月流过之后，唯有“家的味道”能保留下来并愈发珍贵。就像他自己所说的那样：“<strong> 绚烂之极，乃归平淡</strong>”。</p>\n" +
                    "<p align=\"center\" style=\"text-indent: 0px;\"><img id=\"59871512\" alt=\"\" src=\"https://img.gmw.cn/images/attachement/jpg/site2/20180316/f44d305ea8c01c1593183a.jpg\" title=\"炒土豆丝极简史\"></p>\n" +
                    "<p>　　<strong>因为家常，所以普通；因为家常，所以珍贵。土豆丝之所以能在今天南北皆宜成为餐桌常客，也许就是由于这个缘故吧。</strong></p>\n" +
                    "<p align=\"center\">&nbsp;</p>\n" +
                    "                    \n" +
                    "        </div>\n" +
                    "        </div>\n" +
                    "        <!--文章正文 end-->\n" +
                    "        <div class=\"u-conBottomLine\"></div>\n" +
                    "        <!--QRcode-->\n" +
                    "    \t<!-- 新版文章页正文下方二维码 ssi -->\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<div class=\"u-con-left-ad\" id=\"ad_con_left_full_01\" style=\"margin-bottom:20px;\"></div>\n" +
                    "        <!--相关阅读 start-->\n" +
                    "\t\t<meta charset=\"UTF-8\">\n" +
                    "\n" +
                    "        <!-- tplID: nodeID: -->\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "\n" +
                    "    </div>\n" +
                    "    <!--左侧栏 end-->\n" +
                    "    <!--右侧栏 start-->\n" +
                    "    <div class=\"m-r-main no-Mobile\">\n" +
                    "        <!--视觉焦点 start-->\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "<!-- tplID:46078 生活频道全媒体文章页右侧视觉焦点ssi-->\n" +
                    "\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "<!--<div class=\"u-rightad_300x250\" id=\"ad_con_right_02\">\n" +
                    "<div class=\"_tjyb3phfiv\"></div>\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    (window.slotbydup = window.slotbydup || []).push({\n" +
                    "        id: \"u6630769\",\n" +
                    "        container: \"_tjyb3phfiv\",\n" +
                    "        async: true\n" +
                    "    });\n" +
                    "</script>\n" +
                    "\n" +
                    "<script type=\"text/javascript\" src=\"//cpro.baidustatic.com/cpro/ui/cm.js\" async=\"async\" defer=\"defer\" >\n" +
                    "</script>\n" +
                    "</div>-->\n" +
                    "        <!--最热文章 end-->\n" +
                    "        <div class=\"u-rightad_300x250\" id=\"ad_con_right_02\"></div>\n" +
                    "    </div>\n" +
                    "    <!--右侧栏 end-->\n" +
                    "</div>"
        ),
        ArticalEntity(
            7,
            "料酒毁了黄酒？为什么变成了“去香增腥”，难怪越来越多人不用",
            "2023-04-04",
            "早妈美食",
            "https://img-nos.yiyouliao.com/alph/98839dd73ecbcef79fc64b965d148621.jpeg?yiyouliao_channel=xiaomi_image",
            "<p>导语：料酒毁了黄酒，为什么变成了“去香增腥”，难怪越来越多人不用</p>\n" +
                    "  </blockquote> \n" +
                    "  <p data-track=\"16\">生活中少不了一日三餐，想要饭菜可口，除了烹饪技巧之外，一些必备的调料就必不可少，其中“料酒”就是家家户户必备的。</p> \n" +
                    "  <p data-track=\"2\">众所周知料酒是去腥增香的，通常它的制作工艺是用糯米经过浸泡、蒸煮、发酵制作而成，酒精度大概为15度左右。之所以料酒可以去腥，是因为肉类中腥味来源于三甲胺，而料酒中乙醇具有很强的渗透性，可以进入肉质的内部，三甲胺又是熔于乙醇的，乙醇受热挥发后，便会带走三甲胺，因此达到去腥的目的。</p> \n" +
                    "  <p data-track=\"3\">料酒虽然带有“酒”，但是和常见的白酒类不同，它富含脂类，富含氨基酸，所以去腥的同时，料酒还能给食材增加香味。所以无论在饭店还是家庭中，料酒的使用率是越来越高了。</p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/1cef7107ee260f5c22c4b90485f9cee2.jpeg?yiyouliao_channel=xiaomi_image\" img_width=\"1200\" img_height=\"799\" image_type=\"1\" mime_type=\"image/jpeg\" web_uri=\"tos-cn-i-qvj2lq49k0/9cd6cb10c3334cabbaf5a9bfad28ba20\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"4\">其实料酒的历史源远流长，最早可以追溯到3000多年前的夏商时期，但是发展至今，大家有没有发现越来越多的人不喜欢用料酒了，其实并不是因为价格，而是现在的很多料酒既没有增香的作用，相反用后还越来越腥，这是因为什么呢？</p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/4da05d71fa40a67b324a306331da9cc8.png?yiyouliao_channel=xiaomi_image\" img_width=\"730\" img_height=\"512\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/06429f053ba24cc9ae49237f3c813c47\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <h1 class=\"pgc-h-arrow-right\" data-track=\"5\">配料原因</h1> \n" +
                    "  <p data-track=\"6\">黄酒看似普通，但是它有<span style=\"color: #404040; --tt-darkmode-color: #A3A3A3;\">酒香、甘甜、鲜美、去腥、开胃，5种作用，后来人们在黄酒的基础上研发了“烹饪酒”，也就是料酒。品质好的料酒，会在黄酒里面加入香料、调味料制作而成，而品质差的料酒会直接勾兑，加入香精、添加剂等等，反而脱离了“去腥增香”初衷，变成了“去香增腥”。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/bc7d955a70a3759514b2b0d45dfc2f3f.png?yiyouliao_channel=xiaomi_image\" img_width=\"617\" img_height=\"183\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/525a2bd2f0e84460a97f485387886ab4\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"7\">在很多料酒中，我们不难发现，都会有添加焦糖色，这是因为什么呢？其实道理很简单，就是黄酒的含量很少，需要添加焦糖色来改善料酒的颜色，让它呈现出黄酒的颜色。</p> \n" +
                    "  <p data-track=\"8\">这就好比现在的酱油、生抽等等，都是添加了焦糖色，反观几十年的酱油，老远就能闻到那种特有的酱香味道，而现在的酱油还能闻到这种味道吗？</p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/9b912344cf6e7b5d36de2dfe9ffdf70b.jpeg?yiyouliao_channel=xiaomi_image\" img_width=\"750\" img_height=\"500\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/2e2a114adaad4029a94136749f2b78f3\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"9\">另外在料酒中还能看到一种成分“谷氨酸钠”，很多人并不明白它是什么，其实就是“味精”，这东西提鲜的作用非常厉害，但是用在料酒里就成了装在酒瓶的“增鲜剂”，不仅没有增鲜的作用，反而多了一种怪味。</p> \n" +
                    "  <h1 class=\"pgc-h-arrow-right\">料酒还能用吗？</h1> \n" +
                    "  <p data-track=\"11\">其实料酒可以用的，只是购买时要选择品质好的，最好是0添加、0勾兑、0色素的。那些配料表看起来复杂的，还是尽量少用。</p> \n"
        ),
        ArticalEntity(
            8,
            "空气炸锅，真的除了“空气”，什么都可以“炸”吗？哪几款最好吃",
            "2023-04-04",
            "美食达人计划",
            "https://video-nos.yiyouliao.com/contentCenter/acfa5475dc8c7ca2d450e0dc9d9fd7f4.jpg?yiyouliao_channel=msn_image",
            "<p data-track=\"77\">空气炸锅是一种使用热空气进行食物烹饪的小型厨房电器。它可以制作出类似于传统炸锅的炸食，却不用额外放油，因此是一种更加健康的食品烹饪方式。空气炸锅，似乎除了“空气”，什么都可以“炸”，是一款用来制作更加健康的美食重要烹饪工具。今天介绍几种最爱的空气炸锅制作的美食供您参考：</p> \n" +
                    "  <p data-track=\"59\"><strong>一、薯条</strong></p> \n" +
                    "  <div> \n" +
                    "   <img src=\"https://video-nos.yiyouliao.com/contentCenter/4525a619722d26041d8d15c00914e15d.jpg?yiyouliao_channel=msn_image\" img_width=\"1024\" img_height=\"768\" image_type=\"1\" mime_type=\"image/jpeg\" web_uri=\"tos-cn-i-qvj2lq49k0/8a0048fc380d47ed831ae10b5869b467\"> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"4\">薯条是使用空气炸锅最受欢迎的食物之一。它们既美味又容易制作。</p> \n" +
                    "  <p data-track=\"5\">材料：2个中等大小的马铃薯、1汤匙橄榄油、盐和黑胡椒</p> \n" +
                    "  <p data-track=\"9\">步骤：</p> \n" +
                    "  <p data-track=\"52\">1、马铃薯去皮，切成适当大小的条状块，浸泡在清水中15分钟，去除淀粉。</p> \n" +
                    "  <p data-track=\"53\">2、取出马铃薯条，用纸巾或厨房布擦干水分。</p> \n" +
                    "  <p data-track=\"54\">3、在马铃薯条上涂抹橄榄油，并均匀地撒上盐和黑胡椒。</p> \n" +
                    "  <p data-track=\"55\">4、将马铃薯条放入空气炸锅中，将温度设定为摄氏200度，时间设定为15分钟。</p> \n" +
                    "  <p data-track=\"56\">5、当时间到达时，取出炸好的薯条装盘即可。</p> \n" +
                    "  <p data-track=\"58\"><strong>二、空气炸鸡翅</strong></p> \n" +
                    "  <div> \n" +
                    "   <img src=\"https://video-nos.yiyouliao.com/contentCenter/6c60f9f691b8a1a0bd6c3c5ac53e3131.jpg?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"800\" image_type=\"1\" mime_type=\"image/jpeg\" web_uri=\"tos-cn-i-qvj2lq49k0/e29218f5891c4d03bf2a423dd35c855e\"> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"19\">材料：鸡翅膀6个、盐1/2茶匙、黑胡椒粉1/4茶匙、辣椒粉1/4茶匙、面粉1/4杯</p> \n" +
                    "  <p data-track=\"25\">制作过程：</p> \n" +
                    "  <p data-track=\"60\">1、在碗中混合盐，黑胡椒粉和辣椒粉。</p> \n" +
                    "  <p data-track=\"61\">2、将鸡翅膀放入碗中，充分混合，确保每个鸡翅膀都均匀地覆盖了混合物。</p> \n" +
                    "  <p data-track=\"62\">3、将面粉均匀地放在另一个碗中。</p> \n" +
                    "  <p data-track=\"63\">4、将混合好的鸡翅膀一个一个地在面粉中滚动，使其均匀地裹上一层面粉。</p> \n" +
                    "  <p data-track=\"64\">5、将面粉裹好的鸡翅膀放入空气炸锅的篮子中。</p> \n" +
                    "  <p data-track=\"65\">6、设定空气炸锅的温度为180°C，时间为25分钟。</p> \n" +
                    "  <p data-track=\"66\">7、在炸锅的中途，将鸡翅膀拿出来翻转一下，确保它们在两面都受到了充分的加热。</p> \n" +
                    "  <p data-track=\"67\">8、烤好后，将炸好的鸡翅膀放到盘子上，根据需要撒上一些额外的盐和黑胡椒粉，配上一些蔬菜和蘸酱即可享用。</p> \n" +
                    "  <p data-track=\"69\"><strong>三、空气炸鱼排</strong></p> \n" +
                    "  <div> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/inforec-20230329-822e03b48d51cd413ab7f20df4cef237.jpg?yiyouliao_channel=msn_image\" img_width=\"1183\" img_height=\"655\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/8694bf23272f476b9efe8d6620c224a8\"> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"36\">材料：鱼排2块、盐1茶匙、黑胡椒粉1/4茶匙、面包糠1杯、鸡蛋1个、牛奶1/4杯</p> \n" +
                    "  <p data-track=\"43\">制作过程：</p> \n" +
                    "  <p data-track=\"70\">1、在碗中混合盐和黑胡椒粉。</p> \n" +
                    "  <p data-track=\"71\">2、在另一个碗中，将鸡蛋和牛奶混合均匀。</p> \n" +
                    "  <p data-track=\"72\">3、将鱼排在盐和黑胡椒粉混合物中翻滚一下，使其均匀地覆盖上混合物。</p> \n" +
                    "  <p data-track=\"73\">4、将鱼排依次放入混合好的鸡蛋和牛奶中，然后在面包糠中滚动，使其均匀地裹上一层面包糠。</p> \n" +
                    "  <p data-track=\"74\">5、接下来，将鱼排放入预热好的空气炸锅中，以400°F（200°C）的温度烘烤10-12分钟，或者直到鱼排的外部变得金黄酥脆，而内部熟透。您可能需要将鱼排翻转一次，以确保两面均匀烤熟。</p> \n" +
                    "  <p data-track=\"49\">烤好后，将鱼排从空气炸锅中取出，并放在纸巾上吸去多余的油脂。您可以将其与您喜欢的蔬菜和配料一起食用，例如柠檬片、番茄、生菜等等。享受您美味健康的空气炸鱼排！</p> \n"
        )
    )

    val zhuanlanData = listOf(
        ArticalEntity(
            0,
            "发现血糖高了，饮食方面要做到“2忌、3多”，或能帮助稳定血糖",
            "",
            "",
            "",
            "<p id=\"1MCCVTOP\">糖尿病属于三高疾病的一种。我们常说的三高，指的是“高血压，高血糖，高血脂。”糖尿病主要是由于人体里的葡萄糖含量过多，代谢异常导致的。在目前，我国并没有根治糖尿病的办法。只有维持和改善病症。血糖高的人在平时应该格外注意饮食，饮食方面要做到“2忌，3多”。</p>" +
                    "<p class=\"f_center\"><img src=\"https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0402%2F4f6ff5d2j00rsh4si0031c000sf00iym.jpg&thumbnail=660x2147483647&quality=80&type=jpg\"/><br/><br/></p>" +
                    "<p id=\"1MCCVTOR\">有些食物会促进人们的健康，也有的食物会影响人体的健康。食物选得好，疾病都逃跑了。糖尿病患者的血糖很重要，每日都要测血糖含量，根据所测血糖的高低来改善饮食。在日常生活中，各种食物多多少少都有含糖量，只是说每种食物含糖量的多少不同，有的食物含糖量高，也有的食物含糖量低。这就需要血糖高或者糖尿病患者们注意自己食用的食物了。</p>" +
                    "<p id=\"1MCCVTOS\"><strong>甜食虽美味，但也要忌起来</strong></p>" +
                    "<p id=\"1MCCVTOT\">随着社会的发展，食物被人们做成了各种各样。蛋糕，点心，糕点等等都深受人们的喜爱。糖尿病患者群体一般集中于老年人，但部分老年人对糕点还是非常喜欢的。从古至今，糕点一直出现在人们的生活之中。古代的时候，皇宫的御膳房会腌制各种美味糕点给皇上，皇后，格格们和阿哥们吃。</p>" +
                    "<p id=\"1MCCVTOU\">糕点的手法一直传承至今，在不少地方还存在着“老字号糕点”的牌匾。由于糕点的历史久远，自然也是老年人从小就开始吃的食物，所以大部分老年人对糕点还是情有独钟的。但是，大部分糕点都偏甜，对有糖尿病的老年人来说，还是不吃为好。</p>" +
                    "<p class=\"f_center\"><img src=\"https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0402%2F750518bej00rsh4si002dc000xc00mwm.jpg&thumbnail=660x2147483647&quality=80&type=jpg\"/><br/><br/></p>" +
                    "<p id=\"1MCCVTP0\"><strong>油炸食品有很多，但是血糖高的人要忌它</strong></p>" +
                    "<p id=\"1MCCVTP1\">油炸食品的种类有很多，食用油本就是提味提口感的一种调料，但血糖高的人在生活中要少吃。不知道从什么时候开始，每逢过年的时候，人们都会不约而同地炸各种年货。在北方地区，过年各家会炸酥肉，炸鱼，炸豆干等等。</p>" +
                    "<p id=\"1MCCVTP2\">这些食物经过油炸之后，甚是美味。过年的时间基本要从正月三十到大年初七，这个时间里不论男女老少都会吃大鱼大肉。在过年的时候，人们会把很多年货存起来。由于炸得过多，就会把这些东西烩成菜，老年人也很喜欢这个味道。</p>" +
                    "<p id=\"1MCCVTP3\">糖尿病主要是由于胰岛素分泌异常导致的，这种油腻的食物会再次影响胰岛素的分泌，而且还会影响血压和血脂的高低。所以，血糖高的人最好不要吃油炸食品。</p>" +
                    "<p class=\"f_center\"><img src=\"https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0402%2F358d5c4aj00rsh4si001rc000xc00m8m.jpg&thumbnail=660x2147483647&quality=80&type=jpg\"/><br/><br/></p>" +
                    "<p id=\"1MCCVTP5\"><strong>多吃粗粮，少吃粗米饭</strong></p>" +
                    "<p id=\"1MCCVTP6\">不论南方还是北方，米饭一直都作为主食而存在。米饭里面含有大量的碳水化合物，含糖量也是非常高的，如果血糖高的人吃过量的米饭，会导致体内血糖的再次升高。米饭之所以作为人们的主食，是因为米饭的饱腹感很强，能延迟饥饿的时间。</p>" +
                    "<p id=\"1MCCVTP7\">其实除了米饭饱腹感强之外，一些粗粮的饱腹感也是很强的。粗粮里含有大量的膳食纤维，有益于人们的消化和吸收。同时，大部分粗粮里的含糖量也是比较少的，所以糖尿病患者吃粗粮不论是血糖上还是营养上都是一个不错的选择。</p>" +
                    "<p id=\"1MCCVTP8\"><strong>多餐少食，有利于降低血糖</strong></p>" +
                    "<p id=\"1MCCVTP9\">糖尿病患者每天的摄糖量是有限的，如果一次性吃饭过多会增加血糖的含量。少食多餐有利于患者们的消化和吸收，同时又保证了身体所需要的营养。如果一次性吃的食物过多，会给人的消化带来负担，热量也难以消耗，在短时间内血糖就会飙升。所以，对于血糖偏高的人来说，每天多餐少食，有利于降低血糖。</p>" +
                    "<p class=\"f_center\"><img src=\"https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0402%2Fd9898738j00rsh4si000tc000sf00iym.jpg&thumbnail=660x2147483647&quality=80&type=jpg\"/><br/><br/></p>" +
                    "<p id=\"1MCCVTPB\"><strong>多吃蔬菜，有益于身体健康</strong></p><p id=\"1MCCVTPC\">蔬菜里含有大量的膳食纤维，有利于人们的消化和吸收。蔬菜的食用不会对人体的食物消化造成负担，进而就不会影响胰岛素的分泌。蔬菜的含糖量也相对减低，食用蔬菜对血糖并不会造成大的影响。</p>" +
                    "<p id=\"1MCCVTPD\">除此之外，部分蔬菜还有降血脂和降血糖的功效。长时间吃蔬菜，有利于降低血糖，有益于身体健康。</p><p id=\"1MCCVTPE\">高血糖虽然没有根治的办法，但是有维持和改善的办法。高血糖的人除了每天需要注意饮食之外，还要定点定时地测量自己体内的血糖含量。忌甜食，忌油炸食品之外，还要多吃粗粮，多餐少食和多吃蔬菜。维持和改善需要我们自己坚持，可能有时候坚持很困难，但是为了健康，我们必须克服！</p>" +
                    "<p class=\"f_center\"><img src=\"https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2023%2F0402%2F29fba702j00rsh4si001zc000sf00k3m.jpg&thumbnail=660x2147483647&quality=80&type=jpg\"/><br/><br/></p>" +
                    "<p id=\"1MCCVTPG\">糖尿病患者每天也需要一个好的心态，不要过度担心和焦虑。好了，今天我们的分享就到这里了。最后，祝大家生活愉快，身体健康。</p>"
        ),
        ArticalEntity(
            1,
            "尿酸高、痛风？提醒患者：想要尿酸稳定下降，需牢记5个饮食要点",
            "",
            "",
            "",
            "<div class=\"article\" id=\"article\">\n" +
                    "\t\t\t\t<p>　　<font>痛风指的是因体内血尿酸水平过高，从而引起的一种疾病。一旦患病，患者的任何一个关节部位都有可能会发生疼痛或者是畸形现象，严重影响到患者的关节健康。</font></p>" +
                    "<p>　　<font>所以在患病以后，患者必须积极进行治疗。另外，还要做好饮食方面的调整，否则，会导致体内的尿酸水平增高，很容易加重痛风症状。那么，痛风患者饮食上到底该怎么吃呢？</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10110/200/w600h400/20210817/964a-1a6e2a7a45bae4942af674c9ccca9b36.jpg\" w=\"600\" h=\"400\" wh=\"1.50\"/></p><p>　　<strong>痛风患者怎么吃比较健康？</strong></p><p>　　<strong>一、饮食中增加蔬菜摄入</strong></p>" +
                    "<p>　　<font>对于痛风患者来讲，在饮食中必须多增加一些新鲜的蔬菜，尤其是卷心菜、莴笋、甘蓝、芹菜以及各种瓜类蔬菜。</font></p><p>　　<font>这些蔬菜当中的嘌呤含量非常的低，而且含有大量的维生素和膳食纤维。能够提高痛风患者的代谢功能，达到降低尿酸值的作用。</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10111/214/w2048h1366/20220614/e7e0-9456c73bb3b331a71bea9126a6670f07.jpg\" w=\"2048\" h=\"1366\" wh=\"1.50\"/></p>" +
                    "<p>　　<strong>二、低盐低糖饮食</strong></p><p>　　<font>患者的饮食一定要以清淡为主，所有高盐、高糖、高油食物不要摄入。这些食物属于高嘌呤食物，会导致体内的尿酸值升高。而且这些食物的代谢速度较慢，会影响到体内尿酸的排泄。</font></p>" +
                    "<p>　　<font>另外，在烹饪食物时，不可以使用动物油脂，也应该减少植物油的使用量。每天的食盐摄入总量不可以超过6克，一旦食盐量摄入过多，会影响到肾脏的排泄功能。</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10109/613/w848h565/20230304/4dbd-4ced7b61bcb0512daca3756c8219fde7.jpg\" w=\"848\" h=\"565\" wh=\"1.50\"/></p>" +
                    "<p>　　<strong>三、适当补充优质蛋白</strong></p><p>　　<font>患者的饮食中应该适当增加一些富含优质蛋白的食物，比如牛奶以及蛋类。这些食物当中含有较多的优质蛋白，可以为患者提供一定的能量。</font></p><p>　　<font>而对于一些瘦肉或者是鸡鸭肉等肉类，必须煮沸去汤食用。既可以为患者提供较多的蛋白质，还能够避免摄入体内过多的嘌呤物质。</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10116/214/w2048h1366/20220930/b82d-9debb46dc9d322be8140e4813f3df405.jpg\" w=\"2048\" h=\"1366\" wh=\"1.50\"/></p>" +
                    "<p>　　<strong>四、适量摄入碳水化合物</strong></p><p>　　<font>痛风患者的饮食中应适当增加碳水化合物，比如馒头、各种面食、米饭等。这些食物当中的主要成分是淀粉，而且属于低嘌呤的食物，不会增加尿酸值，还有着辅助降低尿酸值的功效。</font></p>" +
                    "<p>　　<font>但值得注意的是，不要吃一些油炸或油煎类的主食，比如油饼或者是油煎包等。</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10111/200/w600h400/20211008/0dba-1347099354b7efdd2a2563772002a0ba.jpg\" w=\"600\" h=\"400\" wh=\"1.50\"/></p>" +
                    "<p>　　<strong>五、限制高嘌呤食物</strong></p>" +
                    "<p>　　<font>所有高嘌呤的食物，患者都应该限制摄入，主要包括各种动物内脏、各种肉汤，以及贝壳类、虾蟹等一些水产品。除此以外，蘑菇、芦笋、各种豆类，以及鸡鸭鹅等食物当中的嘌呤含量为中等量，这些食物也应该减少摄入量。</font></p>" +
                    "<p>　　<font>另外，患者的饮食中不可以摄入任何辛辣刺激性食物，包括各种酒水以及浓茶和咖啡。这些饮品中的嘌呤含量非常高，会导致痛风症状加重。</font></p>" +
                    "<p>　　<img src=\"https://n.sinaimg.cn/sinakd10110/200/w600h400/20210313/cead-kmeeiut4110054.jpg\" w=\"600\" h=\"400\" wh=\"1.50\"/></p><p>　　<font>总而言之，对于痛风患者来说，一定要做好饮食方面的调整。除了以上5点以外，患者每天应适当补充2000毫升以上的水分，能够起到利尿的作用。可以通过排尿将体内多余尿酸排泄出去，达到降低尿酸值的作用。</font></p>" +
                    "<p>　　<font>此外，一定不要出现暴饮暴食的行为，每餐应控制好摄入量，避免体重超标，从而影响到病情的治疗。</font></p>"
        ),
        ArticalEntity(
            2,
            "发现血糖高了，饮食方面要做到“2忌、3多”，或能帮助稳定血糖",
            "",
            "",
            "",
            "<p>心脏疾病被称作威胁人类生命健康的“头号杀手”，美国每年因心脏病死亡的人数大约在70万左右。</p> \n" +
                    "<p>来自纽约西奈山卫生系统的心脏病专家Deepak Bhatt博士，就在近期透露了自己为了保持心脏健康，从来不吃的七种食物。</p> \n" +
                    "<p><span><strong>这份黑名单中，除了快餐、酒精和红肉这三种常见高风险食物之外，还有一些令人意想不到的食物。</strong></span></p> \n" +
                    "<p><strong>01</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>椰子油 </span></strong></p> \n" +
                    "<p>近几年世界刮起的健康饮食之风中，椰子油热度大涨，被认为是非常健康的一种油。</p> \n" +
                    "<p>　　<img src=\"https://tse2-mm.cn.bing.net/th/id/OIP-C.OUmwT5hKmWcypuRk8vMKFAHaEp?pid=ImgDet&rs=1\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p><span><strong>然而，椰子油比猪油含有更多的饱和脂肪，会导致血管中胆固醇的积累。</strong></span></p> \n" +
                    "<p>Bhatt博士表示，椰子油脂肪含量很高，并不适合用来烹饪。它就像所有东西一样， <span><strong>吃太多就会适得其反</strong></span>，从有益健康转变为摄入大量卡路里。 </p> \n" +
                    "<p><strong>02</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>过量蛋白质 </span></strong></p> \n" +
                    "<p>注重健康（尤其是喜欢泡健身房）的小伙伴们肯定都很注重饮食中蛋白质的摄入， <strong>但很多人会忽视蛋白质的摄入量。</strong></p> \n" +
                    "<p>　　<img src=\"https://tse1-mm.cn.bing.net/th/id/OIP-C.s3k9v68g_FFG6td17kf6fAHaEo?w=265&h=180&c=7&r=0&o=5&dpr=1.5&pid=1.7\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p>美国心脏病学会的研究指出，很多人一天摄入的蛋白质是他们可能需要的两倍， <span><strong>会对肾脏造成负担</strong></span>，甚至因为摄入过多饱和脂肪含量高的肉类， <span><strong>导致心脏病和中风</strong></span>等问题。 </p> \n" +
                    "<p>蛋白质的推荐量是每千克体重约0.8克，需要根据自己的运动量调整多吃或者少吃。</p> \n" +
                    "<p><strong>03</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>能量饮料 </span></strong></p> \n" +
                    "<p>能量饮料对心理健康和睡眠的影响已经有很多说法，但它对心脏的影响可能同样糟糕。 </p> \n" +
                    "<p><span><strong>能量饮料除了含有大量卡路里和糖外，还含有大量咖啡因或其它可能升高血压、引起心律失常的物质。</strong></span></p> \n" +
                    "<p>　　<img src=\"https://ts1.cn.mm.bing.net/th/id/R-C.02101d5c1441dae4d7385b72073bd3fe?rik=Aa5GsCGYxORU6w&riu=http%3a%2f%2fwww.jsrhtz.com%2fimages%2fupload%2fImage%2fx5(351).jpg&ehk=Dhc9Jlx%2fs91pfindBs05QZ4iYGuSoeeMZpnTHJd1nxE%3d&risl=&pid=ImgRaw&r=0\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p><strong>04</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>红肉 </span></strong></p> \n" +
                    "<p>摄入红肉会增加 <span><strong>患心脏病和癌症的风险</strong></span>，而它带来的健康风险仅次于烟草。 </p> \n" +
                    "<p>　　<img src=\"https://pic2.zhimg.com/v2-9e639634082196b6792051bf22a43b09_r.jpg\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p>尤其是香肠、培根和萨拉米香肠等加工肉类都 <span><strong>含有大量的卡路里、饱和脂肪和盐</strong></span>，可能会使血压升高，增加心脏病风险。 </p> \n" +
                    "<p>不过红肉富含铁、锌和其他营养物质，完全不吃红肉会导致营养缺乏。所以小伙伴们一定要适量摄入天然红肉，少吃精加工肉类。</p> \n" +
                    "<p><strong>05</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>酒精 </span></strong></p> \n" +
                    "<p><span><strong>酒精会增加肾素激素的血液水平，导致血管收缩，从而增加血压。</strong></span></p> \n" +
                    "<p>Bhatt博士提到，有研究表明，即使是每天只喝一杯酒，也会增加心律失常或室颤的风险。</p> \n" +
                    "<p>为了保持心脏健康，最好避免饮酒。</p> \n" +
                    "<p>　　<img src=\"https://tse1-mm.cn.bing.net/th/id/OIP-C.NzIHQAMSYTGrpBFXXqnCRAHaFz?pid=ImgDet&rs=1\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p><strong>06</strong></p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>快餐 </span></strong></p> \n" +
                    "<p>快餐有多不健康大家心里应该都是有数的吧！</p> \n" +
                    "<p><span><strong>油炸+高盐高糖高热量，经常吃容易肥胖不说，也是导致高脂血症和冠心病的高危食品！</strong></span></p> \n" +
                    "<p>　　<img src=\"https://img.zcool.cn/community/01710b5d22f85aa8012076400d4ab7.jpg@1280w_1l_2o_100sh.jpg\" w=\"600\" h=\"400\" wh=\"1.50\"/></p> \n" +
                    "<p>Bhatt博士补充，快餐的盐分过高，甚至可能会阻止患者服用的药物发挥作用。</p> \n" +
                    "<p style=\"text-align: center;\"><strong><span>小队长碎碎念 </span></strong></p> \n" +
                    "<p>这几年来全世界对健康饮食的重视度越来越高，不知道会不会有吃货小伙伴觉得： <strong>这也不能吃那也不能吃，人生还有意思吗？</strong></p> \n" +
                    "<p><strong>人生就活这么几十年，当然要吃自己喜欢的食物啦！</strong></p> \n" +
                    "<p>而且 <span><strong>少吃≠不吃</strong></span>，爱吃垃圾食品的小伙伴可以给自己设置每周/每月一次，既解馋也不至于危害身体健康。 </p> \n" +
                    "<p><strong>最重要的是：平时一定要注意饮食均衡，摄入足够的各种营养素，积极锻炼加强身体素质，才能远离疾病，吃嘛嘛香！</strong></p> "
        ),
    )

    var list = listOf(
        ArticalEntity(
            0,
            "一祛湿，二散寒，三助眠，春天遇到别手软",
            "2023-03-30",
            "有料美食",
            "https://img-nos.yiyouliao.com/alph/e806019b337bfd12dd01160ab5daf4fc.png?yiyouliao_channel=msn_image",
            "<div>\n" +
                "<article class=\"syl-article-base tt-article-content syl-page-article syl-device-pc\">\n" +
                "  <p data-track=\"1\"><strong>导语：</strong>一祛湿，二散寒，三助眠，春天遇到别手软，尤其女性要常吃，活血暖宫祛湿寒</p> \n" +
                "  <p data-track=\"2\">大家好我是傻姐美食，说到艾草相信许多朋友都不陌生吧，咱们平时做艾灸用的艾条，里面就是艾草的成份。艾草不仅能祛湿散寒还有助于睡觉，对身体非常的有好处。每年清明前后的艾草最是鲜嫩了，许多懂吃的朋友，都会摘些回来用来泡茶或做成美食。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/e806019b337bfd12dd01160ab5daf4fc.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"760\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/e323db9aead34bbca5a761ce8b5502ca\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"3\">艾草属于菊科蒿属植物，有的地方也跟艾草叫艾蒿。它独特的气味还具有驱蚊的效果，每年到了端午节的时候，家家户户门上都会插上艾蒿，而且一些上了年纪的老人，还会用艾草做成许多“香包”，给自己家的孩子戴上，据说可以驱除“邪气”带来好运。不过到那时候的艾蒿就已经很老了也不好吃了，所以想吃艾蒿的朋友一定要趁早采摘，今天小编就分享大家2种艾蒿的做法，清香软糯特别好吃，大家一起来看看吧，</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/d19d3fe6867ffcbe947c052ea455fc6c.png?yiyouliao_channel=msn_image\" img_width=\"1198\" img_height=\"867\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/648ffe51918946a3b9a5196e787f5010\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"4\"><strong>第一种：【艾草糍粑】</strong></p> \n" +
                "  <p data-track=\"5\"><strong>准备食材：</strong>艾草、花生碎、红糖、食用油</p> \n" +
                "  <p data-track=\"6\"><strong>具体步骤：</strong>1、清明游玩踏青的时候，带上点自己做的青团既实惠又美味。摘回来的艾草叶放入盆中，加入清水和少许盐浸泡一会，然后放到流动的水下揉洗干净。锅中加入清水少许食用碱大火烧开，放入洗好的艾草焯一下热水，然后捞出来控下水份，放入搅拌机里打成糊状。打好的艾草倒入盆中，加入适量的糯米粉，少许食用油揉搓成光滑的面团。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/54ba23a43fcdb3707b0689ed75ef0a67.png?yiyouliao_channel=msn_image\" img_width=\"1198\" img_height=\"858\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/e7fc86fcc2ac48749dc1a10b9da18c7b\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"7\">2、揪出适量面团搓成圆形压扁，放入花生碎和红糖然后收口，再团成圆形压扁垫上玉米皮。团好的糍粑放入蒸锅，大火水开后蒸15分钟就可以出锅了。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/88496b1e5ea388e4e7848f79d733db47.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"674\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/b618be29821b4cf5abc5bf299d3653c4\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"8\"><strong>第二种：【艾草鸡蛋饼】</strong></p> \n" +
                "  <p data-track=\"9\"><strong>准备食材：</strong>艾草、鸡蛋、面粉、盐、食用油</p> \n" +
                "  <p data-track=\"10\"><strong>具体步骤：</strong>1、盆中加入适量清水和少许盐，放入艾蒿叶浸泡一会儿，然后清洗干净。锅中加入清水少许盐，水开后放入艾蒿叶焯一下热水，捞出后放入冷水中过凉，然后取出来控下水份放入盆中。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/8bdd360ca7496e541ad2fbfbd1a064e0.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"675\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/519176540569459f83bae62ce2d16105\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"11\">2、盆中加入适量面粉，（少量多次加入）用手把面粉和艾叶抓拌均匀，使艾叶充分的裹上一层面粉，然后再打入一个鸡蛋、放入少许盐用筷子把它们搅拌均匀。（如果调的面糊稍微厚点，可以少兑入点纯净水。）</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/d983d5ae61c8138103668fcc02c31f06.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"674\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/f109a3ea84b746bba52ef73006eb0cc1\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"12\">3、电饼铛里刷少许食用油，用勺子取适量面糊放入平底锅里，开小火慢煎一面定型后，用铲子翻到另一面，两面都煎熟以后就可以取出了，清香软糯非常好吃。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/bdc7e26f5360f319025376e4c447bb45.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"675\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/6a89a7ab98a14428889dd3ae0d3123a8\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"13\"><strong>温馨小提示：</strong>1、春季是好发过敏的季节，如果患有皮肤病和易过敏的朋友，最好不要食用艾草。2、月经量大过多的女性朋友，也尽量避免在经期食用艾草。</p> \n" +
                "  <div class=\"pgc-img\"> \n" +
                "   <img src=\"https://img-nos.yiyouliao.com/alph/241584da23c0ff738030851aba074b61.png?yiyouliao_channel=msn_image\" img_width=\"1200\" img_height=\"675\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/dcd3d6e114b14e93b26959f3d119b1cc\">\n" +
                "   <p class=\"pgc-img-caption\"></p> \n" +
                "  </div> \n" +
                "  <p data-track=\"14\">艾草不仅是一种很好吃的野菜，同时也具有较高的“药用价值”，平时有痛经宫寒的女性朋友，也可以多食用些艾草，不仅活血暖宫对身体非常的有好处。今天艾草的2种做法分享到这里，希望我的分享能让您有所收获，感谢您的观看和支持。</p> \n" +
                " </article>"
        ),
        ArticalEntity(
            1,
            "春天建议：少吃辛辣和油腻！推荐5道家常菜，清爽不油腻，快尝尝",
            "2023-03-31",
            "王姐美食",
            "https://img-nos.yiyouliao.com/alph/6bd52df0ae00691031b314cdf62f9537.png?yiyouliao_channel=msn_image",
            "<div>\n" +
                    " <article class=\"syl-article-base tt-article-content syl-page-article syl-device-pc\">\n" +
                    "  <p data-track=\"1\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">导语：春天建议：少吃辛辣和油腻！推荐5道家常菜，清爽不油腻，快尝尝</span></p> \n" +
                    "  <p data-track=\"3\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">春天是一个比较干燥的季节，同时又是适合养生的季节，在饮食上需要多注意。春天建议：少吃辛辣和油腻！尽量以清淡饮食为主，这样做可以保护肝脏、脾胃、肠道健康，对身体大有好处。</span></p> \n" +
                    "  <p data-track=\"4\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">给大家推荐5道家常菜，清爽不油腻，好吃不上火，春天多吃对身体好，快尝尝。做法详细，不妨照做，越吃越健康。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/6bd52df0ae00691031b314cdf62f9537.png?yiyouliao_channel=msn_image\" img_width=\"603\" img_height=\"504\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/a7d68141a2784de3acf243b7715e5cca\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"5\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">一、番茄西葫芦菌菇炒鸡蛋</span></p> \n" +
                    "  <p data-track=\"6\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"7\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">准备好食材，分别有番茄、西葫芦、白玉菇、鸡蛋。</span></p> \n" +
                    "  <p data-track=\"8\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">番茄清洗干净，顶部划十字，放进开水中浸泡两三分钟，再将外皮去掉，切成小块待用。</span></p> \n" +
                    "  <p data-track=\"9\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">西葫芦清洗干净，切成薄片，不喜欢吃皮可以去掉。白玉菇去掉根部，清洗干净，再切成小段。鸡蛋打进碗中搅散。</span></p> \n" +
                    "  <p data-track=\"10\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加油，烧热后倒入蛋液，炒成小块盛出。重新加油烧热，放入蒜末爆香，再倒入西葫芦翻炒。</span></p> \n" +
                    "  <p data-track=\"11\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">炒一分钟后，倒入番茄、白玉菇继续翻炒，炒出水分。</span></p> \n" +
                    "  <p data-track=\"12\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后倒入鸡蛋翻炒均匀，再加入盐、蚝油，快速炒均匀，这道菜就做好了。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/5b0480770ca62fdc6cb314e5a44e5d01.png?yiyouliao_channel=msn_image\" img_width=\"703\" img_height=\"553\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/61453866faca492daa90e9f77531e6bc\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"13\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">二、丝瓜炒虾仁</span></p> \n" +
                    "  <p data-track=\"14\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"15\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">大虾去掉虾头、虾壳、虾线，冲洗后沥干水分。然后加入料酒、姜丝、胡椒粉、淀粉抓拌均匀，腌制五分钟。</span></p> \n" +
                    "  <p data-track=\"16\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">丝瓜去皮洗净，切成滚刀块。蒜片、姜丝准备好。</span></p> \n" +
                    "  <p data-track=\"17\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加油，烧热后放入虾仁翻炒，炒到变色时盛出。接着倒入姜蒜爆香，再倒入丝瓜翻炒，炒到微软。</span></p> \n" +
                    "  <p data-track=\"18\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后倒入虾仁翻炒均匀，再加少许盐、生抽，快速翻炒出锅。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/76421d660c2694f14730af36d70a0632.png?yiyouliao_channel=msn_image\" img_width=\"742\" img_height=\"524\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/bbd3345356634efbaf263a192ee8bed5\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"19\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">三、冬瓜肉丸汤</span></p> \n" +
                    "  <p data-track=\"20\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"21\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">猪肉去皮，冲洗干净，切成小块放进料理机，打成泥状。生姜去皮洗净，切成姜蓉，越碎越好。</span></p> \n" +
                    "  <p data-track=\"22\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">肉泥中放入姜蓉、盐、白胡椒粉、生抽、鸡精、鸡蛋、朝着一个方向搅拌上劲。</span></p> \n" +
                    "  <p data-track=\"23\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">冬瓜去皮洗净，切成厚片待用。香葱洗净，切成葱花待用。</span></p> \n" +
                    "  <p data-track=\"24\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加水，煮开后转小火，抓肉馅从手的虎口挤出，放进锅内。</span></p> \n" +
                    "  <p data-track=\"25\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">肉丸都浮起来后，放入冬瓜，保持小火煮两分钟。时间到后转中火，煮到肉丸和冬瓜熟透。</span></p> \n" +
                    "  <p data-track=\"26\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后加盐、鸡精调味，再倒入少许香油，撒些葱花即可出锅。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/11b9ed0fea93597aa0a442eb8b3456f3.png?yiyouliao_channel=msn_image\" img_width=\"680\" img_height=\"498\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/2b17084e30ff40a3b96665c9f691125a\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"27\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">四、裙带菜豆腐鸡蛋汤</span></p> \n" +
                    "  <p data-track=\"28\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"29\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">裙带菜放进碗中，加入清水浸泡。等裙带菜泡软后，清洗几遍，再切成小块。豆腐切成小块待用。</span></p> \n" +
                    "  <p data-track=\"30\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加油，烧热后转小火，打入鸡蛋，煎成荷包蛋。煎好后用锅铲弄成小块，或者打散后炒成小块。</span></p> \n" +
                    "  <p data-track=\"31\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">接着给锅内放入葱花，再倒入裙带菜，翻炒半分钟。</span></p> \n" +
                    "  <p data-track=\"32\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后倒入适量清水，大火煮开，放入豆腐和鸡蛋，加少许盐煮两分钟。时间到后加鸡精搅拌均匀，就可以关火了。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/ae6f2bbf05916c4a97c85a3238468742.png?yiyouliao_channel=msn_image\" img_width=\"759\" img_height=\"552\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/7b1c016ec3f944f2a997e6c85fe38b1a\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"33\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">五、金针菇蒸鸡翅</span></p> \n" +
                    "  <p data-track=\"34\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"35\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">鸡翅斩成两半，横切或者纵切都可以，处理好后清洗几遍，沥干水分。</span></p> \n" +
                    "  <p data-track=\"36\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">然后加入料酒、姜丝、生抽、蚝油、白胡椒粉、淀粉抓拌均匀，腌制半个小时。金针菇去掉根部，清洗干净，放进盘内摆好。</span></p> \n" +
                    "  <p data-track=\"37\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">鸡翅腌好后，放在金针菇上摆好，再放入蒸锅，大火蒸十五分钟。时间到后撒些胡萝卜丁，再继续蒸五分钟。</span></p> \n" +
                    "  <p data-track=\"38\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">蒸好后，将盘内的水倒进锅内，再加入少许生抽、淀粉水勾芡，倒进盘内，撒些葱花，就可以食用了。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/72ad9f1b0580b6054083c784b69327e5.png?yiyouliao_channel=msn_image\" img_width=\"695\" img_height=\"531\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/a556d86964534046af9df6d2baaefd82\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"39\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">以上五道菜清爽不油腻，适合春天吃，快做出来尝尝，健康度春。</span></p>\n" +
                    " </article>"
        ),
        ArticalEntity(
            2,
            "蒸包子时，不要直接上锅蒸，多做一步，蓬松暄软，不回缩不塌陷",
            "2023-04-02",
            "渭滨生活汇",
            "https://img-nos.yiyouliao.com/alph/7077cf474748993aec210b86261aaa94.jpeg?yiyouliao_channel=msn_image",
            "<p>包子，是中国的传统美食，可以说能够俘获大部分人的心。包子看似简单，不怎么难做，可是如果想包出好吃好看，蓬松暄软，不回缩不塌陷的包子还是需要很多小技巧的，真可谓是小包子有大技巧。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/7077cf474748993aec210b86261aaa94.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/7077cf474748993aec210b86261aaa94.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>在蒸包子时有人喜欢热水下锅，有人喜欢冷水下锅，有人蒸出来同一锅包子，有的很漂亮，白白胖胖的，有的却是硬的和死面疙瘩一样，有的光滑，有的坑坑洼洼，今天我就详细的给大家来介绍包包子的方方面面，希望大家都能蒸出白白胖胖，漂漂亮亮的大包子。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/5f42dfe40983b55268e5f5e416fe3283.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/5f42dfe40983b55268e5f5e416fe3283.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>今年，猪肉已经成功逆袭了，我们这的猪肉一天一个价，以前去菜市场晚了就买不到好的猪肉了，现在下午去还有很多，看来猪肉已经让很多人望而却步了。今天就来包一个素包子吧，儿子喜欢吃胡萝卜，我又在馅里面加了鸡蛋，粉丝，也是非常的营养美味的，下面就一起来看一下具体的制作过程吧。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/4c27eea5252b22bcddebb456ee3705af.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/4c27eea5252b22bcddebb456ee3705af.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>1.和面：在面粉中加入酵母，分次加入250毫升温水，边加边搅拌，搅拌成絮状以后下手揉成光滑的面团，盖上保鲜膜醒发至2倍大。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/d146d9331816192d1bd79081d369ff81.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/d146d9331816192d1bd79081d369ff81.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>2.调馅：鸡蛋放入碗中打散，锅里放油，放入蛋液快速翻炒，炒散炒熟后盛出备用。泡好的粉丝在开水里煮一下，变软后捞出，切几刀，这样更容易包，木耳提前泡发，切碎，胡萝卜切丁。分别装入碗中，放入盐、鸡粉、蚝油，搅拌均匀馅料就做好了。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/4b6d94d57f25e86ce4b669770ca62186.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/4b6d94d57f25e86ce4b669770ca62186.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/928dba49ae26ef668c530d6f5585ceb4.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/928dba49ae26ef668c530d6f5585ceb4.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>3.发好的面在表面插一个洞，动口不回缩，扒开面团里面有漂亮的蜂窝状，表示面已经发好，取出揉搓排气。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/71df70c92badd3d5614f05511ebe193f.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/71df70c92badd3d5614f05511ebe193f.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>4.把揉搓好的面团整理成长条形，分成大小均匀的面剂，把每个面剂按扁，擀成圆形的包子皮。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/aa1eca4b43b7585d67d72974df75e0da.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/aa1eca4b43b7585d67d72974df75e0da.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/bad619b799b8b87a1bb8219044ed67ed.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/bad619b799b8b87a1bb8219044ed67ed.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>5.取一个面皮，放入馅料，多放一些，包好，封口捏紧。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/58e26e19733f4997bfb0441665b2567b.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/58e26e19733f4997bfb0441665b2567b.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/c0d0f9e04cd29d43bc424c34a23f47a5.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/c0d0f9e04cd29d43bc424c34a23f47a5.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>6.蒸屉里刷油，放入包好的包子，盖上锅盖二次醒发15分钟。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/b134a58c0e2bb4092ee035212be36762.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/b134a58c0e2bb4092ee035212be36762.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>7.醒发好以后先大火烧开，再转中火蒸15分钟，关火再焖5分钟就可以开盖出锅了。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/8d0f40656158c793a0deba79174b29eb.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/8d0f40656158c793a0deba79174b29eb.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/bffbd2686c4022ab18163331ce0740a1.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/bffbd2686c4022ab18163331ce0740a1.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>1.如果大家要在早晨包包子，可以晚上睡前把面和馅调好，放冰箱冷藏室保存一晚，第二天早晨起来面就已经发好了，早餐也可以吃到新鲜的大包子了。</p> \n" +
                    " <p>2.发好的面团一定要多揉搓排气，排净里面的大气泡，这样做出来的包子表面才会光滑，内部才会细腻，无气孔。</p> \n" +
                    " <p>3.素包子一定要尽可能多的放馅料，否则蒸出来的包子容易憋，不饱满。</p> \n" +
                    " <p>4.包好的包子千万不要直接上锅蒸，一定要二次醒发到位，再次激起酵母的活性，这样蒸出来的包子才会蓬松柔软，醒发好的包子体积变大，同时轻轻的按压表面可以缓慢回弹，有这种状态时就可以开火了。</p> \n" +
                    " <p>5蒸包子时尽量冷水下锅，让包子和水慢慢的一起升温，这样可以使包子受热均匀，大火烧开以后要转中火蒸，一直用大火蒸容易造成水蒸气低落，使包子表面坑坑洼洼的状况。蒸好的包子不要着急开盖，关火后再焖5分钟开盖，可以防止因急速受冷后引起回缩，同时开盖时也要防止水蒸气低落。一般素包子、馒头、花卷蒸15分钟就可以，肉包蒸20分钟就可以，当然也要看包子的大小。</p> \n" +
                    " <p>这样做出来的包子薄皮大馅，蓬松暄软，虽然是素馅，但是营养美味。其实蒸包子真的不难，只要注意发面、排气、二次醒发、蒸制，注意几点小细节，都可以做好。做出来的包子都会白白胖胖，不会回缩，不会塌陷的。</p>"
        ),
        ArticalEntity(
            3,
            "素菜这么搭配太好吃了，健康低脂口感佳，清淡爽口又下饭",
            "2023-04-02",
            "渭滨生活汇",
            "https://img-nos.yiyouliao.com/alph/cc1f204a84f544468f2a6c29a38a84e9.jpeg?yiyouliao_channel=msn_image",
            " <p>女人是辛苦的，当了妈妈的女人更辛苦，当了妈妈还要上班的女人那就是“没有最辛苦，只有更辛苦“。在外面忙碌的一整天，回到家里面，男人们都是躺在沙发上玩手机，而女人们放下包，就要准备晚饭，得伺候孩子们吃喝。如果家里那位不够给力的话，那生活简直堪比地狱啊。</p> \n" +
                    " <p>推荐一道简单的快手炒菜，食材搭配营养全面，健康低脂口感好，味道清淡爽口，关键还特别简单，即使是厨房新手也能轻松搞定，十分钟就能上桌，特别适合上班族。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/8f36e7e6b4632f467f62308b6bbe130b.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/8f36e7e6b4632f467f62308b6bbe130b.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>素菜这么搭配太好吃了，健康低脂口感佳，清淡爽口又下饭。</p> \n" +
                    " <p>西兰花营养价值很高，常吃可以提高身体抵抗力，还能清肠减脂。和口蘑一起炒，营养全面，口感丰富，素菜也可以很美味。你也试试看吧。</p> \n" +
                    " <p>【西兰花炒口蘑】</p> \n" +
                    " <p>【所需食材】：西兰花1颗，口蘑8个，青椒半个，红椒半个，大蒜1瓣，大葱1小段，蚝油1大勺，盐1勺，食用油1勺。</p> \n" +
                    " <p>【具体制作步骤】：</p> \n" +
                    " <p>1.准备好所有的食材。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/cb2d987b538be76a85cd9152fa710896.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/cb2d987b538be76a85cd9152fa710896.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>2.将西兰花清洗干净，掰成适口的小朵，放入淡盐水中浸泡十五分钟。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/b27a75763ca3c3393f0728bb13771835.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/b27a75763ca3c3393f0728bb13771835.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>3.将口蘑清洗干净，改刀切成厚薄均匀的片状。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/53594fc4d90d07b47e888b3897ba6cf0.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/53594fc4d90d07b47e888b3897ba6cf0.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>4.将青红椒清洗干净，去蒂去籽，改刀切成块状，大蒜切成薄片，葱段切成圈。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/c5acf5ccbbbd508c60e79f2a36f3cf25.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/c5acf5ccbbbd508c60e79f2a36f3cf25.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>5.锅中放入清水，大火煮至沸腾，加入少许盐和食用油，然后将西兰花放入沸水锅中，焯烫30秒左右，捞出来浸入凉水里面降温，然后沥干水分备用。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/05cb375f5a3c258c0cd4864ee58ecfa4.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/05cb375f5a3c258c0cd4864ee58ecfa4.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>6.锅烧热，放入适量食用油，油微热的时候，将葱花放入锅中，中小火煸炒出香味。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/770a946a300bb9b4219630208c36bb1e.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/770a946a300bb9b4219630208c36bb1e.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>7.将口蘑放入锅中，中火煸炒，随着加热，口蘑会慢慢出来很多水分，将口蘑煸炒至变软。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/9e5313ccaea389c4d7c2643c9e601a9a.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/9e5313ccaea389c4d7c2643c9e601a9a.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>8.将沥干水分的西兰花倒入锅里面，大火翻炒均匀。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/1267b1c635b7fe2934d4577b720b56d6.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/1267b1c635b7fe2934d4577b720b56d6.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>9.往锅中加入适量蚝油和少许盐，翻炒均匀。蚝油是有咸味的，盐要少放。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/8b8bfc2fb727103e7c18dbb95127522b.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/8b8bfc2fb727103e7c18dbb95127522b.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>10.将青红椒放入锅中，大火翻炒大约20秒，将青红椒翻炒断生就可以了。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/aa1c375a3e7338d921c942576d43011c.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/aa1c375a3e7338d921c942576d43011c.jpeg?yiyouliao_channel=msn_image\"></p> \n" +
                    " <p>【小贴士】：</p> \n" +
                    " <p>1.西兰花焯水可以减少烹饪的时间，口感会更加脆嫩。焯水的的时候，在水里加入少许食用油和少许盐，可以保持西兰花翠绿的颜色。</p> \n" +
                    " <p>2.口蘑切薄片，更容易熟。口蘑炒的时间长了会破坏其营养价值，而且还容易变黑。</p> \n" +
                    " <p>3.青红椒只是为了配色用，如果没有，不放也可以。</p> \n" +
                    " <p class=\"ql-align-center\"><img max-width=\"600\" data-src=\"https://img-nos.yiyouliao.com/alph/8f561f10431ee98afecf6de624b05124.jpeg?yiyouliao_channel=msn_image\" src=\"https://img-nos.yiyouliao.com/alph/8f561f10431ee98afecf6de624b05124.jpeg?yiyouliao_channel=msn_image\"></p>"
        ),
        ArticalEntity(
            4,
            "春天不减肥，夏天徒伤悲！推荐5道刮油菜，低脂低热量，越吃越瘦",
            "2023-04-03",
            "邹哥美食",
            "https://img-nos.yiyouliao.com/alph/2aacb0bd2c3170806e25abfe969022bf.png?yiyouliao_channel=msn_image",
            "<div>\n" +
                    "<article class=\"syl-article-base tt-article-content syl-page-article syl-device-pc\">\n" +
                    "  <p data-track=\"1\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">导语：春天不减肥，夏天徒伤悲！推荐5道刮油菜，低脂低热量，越吃越瘦</span></p> \n" +
                    "  <p data-track=\"3\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">眼看三月马上就要过完了，不知道你瘦了几斤？大家经常说：“春天不减肥，夏天徒伤悲”，距离夏天越来越近了，趁现在抓紧行动吧，别再偷懒了，不然到时候就要哭着说后悔。</span></p> \n" +
                    "  <p data-track=\"4\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">减肥离不开运动，但吃得对不对也很重要。给大家推荐5道刮油菜，低脂低热量，想减肥多吃些，越吃越瘦！快来看看吧，不妨照做，再配合运动，不瘦都难。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/d5f3b2f320a53304d19d94342ae8fb09.png?yiyouliao_channel=msn_image\" img_width=\"778\" img_height=\"566\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/28d773080da44e90bfaa6b79f1b33c50\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"5\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">一、菠菜玉米面菜团</span></p> \n" +
                    "  <p data-track=\"6\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"7\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">菠菜清洗干净，开水入锅烫一下。然后菠菜过凉，挤干水分，再剁得碎一点。</span></p> \n" +
                    "  <p data-track=\"8\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">菠菜剁好后装进大碗，加一勺面粉和一勺玉米面，搅拌均匀。</span></p> \n" +
                    "  <p data-track=\"9\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">接着下手捏成小团子，放进蒸屉内。都做好后开水入锅蒸二十分钟，蒸的过程中调一份蘸料，比如放生抽、醋搅拌均匀。</span></p> \n" +
                    "  <p data-track=\"10\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">等蒸熟后，蘸着料汁吃就行。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/2aacb0bd2c3170806e25abfe969022bf.png?yiyouliao_channel=msn_image\" img_width=\"682\" img_height=\"513\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/d9aafffdaba94ea79fd1c8d9d047b05b\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"11\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">二、紫甘蓝拌木耳</span></p> \n" +
                    "  <p data-track=\"12\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"13\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">木耳放进碗中，加入温水浸泡，泡发后去掉根部，反复洗几遍，再撕成小朵待用。</span></p> \n" +
                    "  <p data-track=\"14\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">紫甘蓝分成一片一片，清洗之后切成细丝，越细越好吃。蒜末、香菜碎准备好。</span></p> \n" +
                    "  <p data-track=\"15\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加水，煮开后倒入木耳，煮三五分钟。煮好后立即过凉，再沥干水分。</span></p> \n" +
                    "  <p data-track=\"16\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">木耳、紫甘蓝放在一起，加入盐、白糖、生抽、香醋、花椒油、辣椒油、蒜末、香菜搅拌均匀，这道菜就做好了。</span></p> \n" +
                    "  <p data-track=\"17\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">装盘后，撒些熟白芝麻点缀即可。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/03070233cd19209a2fbf91e041e9fbd1.png?yiyouliao_channel=msn_image\" img_width=\"737\" img_height=\"541\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/1bb53391cb5d48f2bcfb20012d25e8d2\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"18\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">三、松仁玉米</span></p> \n" +
                    "  <p data-track=\"19\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"20\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">新鲜玉米剥下玉米粒，剥好后冲洗干净。青红椒清洗干净，切成小粒。松子仁准备好。</span></p> \n" +
                    "  <p data-track=\"21\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内加水，煮开后倒入玉米粒，煮五分钟捞出，沥干水分待用。</span></p> \n" +
                    "  <p data-track=\"22\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">锅内收拾干净，倒入松子仁，小火翻炒，炒出香味盛出。</span></p> \n" +
                    "  <p data-track=\"23\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">接着加入少许油，烧热后倒入青红椒翻炒，炒到断生，再倒入玉米粒翻炒，加盐调味。</span></p> \n" +
                    "  <p data-track=\"24\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后倒入松子仁快速炒几下，就可以出锅了。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/66b32d74dbaed5f79906822b6b46ecaa.jpeg?yiyouliao_channel=msn_image\" img_width=\"1284\" img_height=\"1038\" image_type=\"1\" mime_type=\"image/jpeg\" web_uri=\"tos-cn-i-qvj2lq49k0/f0036c4ba16b4b72ba1c218aefecf019\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"25\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">四、黄瓜拌鸡蛋</span></p> \n" +
                    "  <p data-track=\"26\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"27\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">鸡蛋清洗干净，冷水入锅煮开，再转小火煮十分钟。煮熟后捞出，用冷水浸泡，再将鸡蛋壳去掉，切成小块。</span></p> \n" +
                    "  <p data-track=\"28\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">黄瓜搓洗干净，用刀拍裂开，再切成小段。</span></p> \n" +
                    "  <p data-track=\"29\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">黄瓜装进碗中，放入蒜末、盐、白糖、生抽、香醋、辣椒油搅拌均匀。</span></p> \n" +
                    "  <p data-track=\"30\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">最后放入鸡蛋轻轻翻拌几下，就可以食用了。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/18e45f308fc6fba0577204d813742975.png?yiyouliao_channel=msn_image\" img_width=\"730\" img_height=\"569\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/326b43965bd44874bbd500471188938b\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"31\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">五、豆皮蔬菜卷</span></p> \n" +
                    "  <p data-track=\"32\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">做法：</span></p> \n" +
                    "  <p data-track=\"33\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">豆皮冲洗一下，再入锅煮两分钟，煮过后更加卫生。</span></p> \n" +
                    "  <p data-track=\"34\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">蔬菜部分随意准备，比如黄瓜、胡萝卜、紫甘蓝、香菜等，能生吃的蔬菜切成细丝，不能生吃的入锅焯水断生。</span></p> \n" +
                    "  <p data-track=\"35\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">豆皮内放入各种蔬菜，摆好后卷起来，在斜刀切成小段，放进盘内摆好。</span></p> \n" +
                    "  <p data-track=\"36\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">炒个鸡蛋酱和豆皮卷搭配着吃。鸡蛋打进碗中搅散，入锅炒成小块盛出。</span></p> \n" +
                    "  <p data-track=\"37\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">接着锅内加油烧热，放入甜面酱翻炒，再倒入鸡蛋、盐翻炒均匀即可。不想麻烦的话，简单地调个料汁，蘸着吃就行，也挺不错的。</span></p> \n" +
                    "  <div class=\"pgc-img\"> \n" +
                    "   <img src=\"https://img-nos.yiyouliao.com/alph/7e9098e44e7dc49cbf1f82c51cbfa5e3.png?yiyouliao_channel=msn_image\" img_width=\"740\" img_height=\"556\" image_type=\"1\" mime_type=\"image/png\" web_uri=\"tos-cn-i-qvj2lq49k0/3852c9a33acc4936acf29de8fbc87639\">\n" +
                    "   <p class=\"pgc-img-caption\"></p> \n" +
                    "  </div> \n" +
                    "  <p data-track=\"38\"><span style=\"color: #333333; --tt-darkmode-color: #A3A3A3;\">以上五道家常菜简单易做，低脂低热量，好吃助消化，快行动起来吧，助你越吃越瘦。</span></p>\n" +
                    " </article>"
        ),
        ArticalEntity(
            5,
            "烹调油，你买对了吗",
            "2023-04-04",
            "中国食品工业杂志",
            "https://img-s.msn.cn/tenant/amp/entityid/AA18jnIB.img?w=450&h=234&q=90&m=6&f=jpg&u=t",
            "<div>\n" +
                    "<article class=\"syl-article-base tt-article-content syl-page-article syl-device-pc\">\n" +
                    "  <p>市场上的烹调油种类很多，让人眼花缭乱，到底平常烹饪用什么油比较好呢，很多消费者对此比较困惑，下面就来盘点一下各类烹调油。</p> \n" +
                    "  <p>不同油的区别主要在脂肪酸，烹调油有的饱和脂肪酸多一些，有的不饱和脂肪酸多一些；不同的油脂对健康有着不同的影响，耐热性也不一样。</p> \n" +
                    "  <p><strong>饱和脂肪酸</strong></p> \n" +
                    "  <p>饱和脂肪酸含量一般来说是猪牛羊油脂＞鸡鸭油脂＞鱼、植物油脂。但也有例外，棕榈油以及椰子油就不符合这个规律，椰子油以高达86%的饱和脂肪酸含量登上榜首。</p> \n" +
                    "  <p>饱和脂肪酸还是应该少吃，少吃饱和脂肪酸，能降低血清胆固醇，从而减少心血管疾病发生。研究显示，大约59000名参与者少吃饱和脂肪酸2年以上，心血管疾病发作风险降低了21%。</p> \n" +
                    "  <p>研究显示，人体应当限制摄入饱和脂肪酸，它所提供的热量不应超过每天总热量的10%。</p> \n" +
                    "  <p>对于一个普通轻体力成年女性，每天需要热量1800千卡，10%换算成脂肪就是20克的饱和脂肪酸限量，仅23克椰子油就能达到了，黄油是38克，猪油是48克。但人体不只从烹调油中摄入饱和脂肪酸，还有肉、奶等。如果食用肉类比较多，建议别用这些油烹饪。</p> \n" +
                    "  <p>偶尔用饱和脂肪酸高的油，做一次油炸食品解馋的话也是可以的。油炸不健康的原因不仅仅在于食物大量吸油带来的高热量，尤其是油反复使用时，有害物质不断积累。但饱和脂肪酸结构稳定耐高温，即使长时间保持高温产生的有害物质也少，也不可能变成反式脂肪酸。所以控制吃油炸的频率只是偶尔的前提下，用棕榈油、椰子油炸比那些不饱和脂肪酸多的植物油好。</p> \n" +
                    "  <p><strong>单不饱和脂肪酸</strong></p> \n" +
                    "  <p>多年蝉联最佳饮食榜首的地中海饮食，其特征之一就是使用橄榄油作为烹饪油。橄榄油名声在外，就是因为它单不饱和脂肪酸占比很高，更具体地说，是富含油酸。</p> \n" +
                    "  <p>一方面，单不饱和脂肪酸对心血管健康比较友好，涉及32项队列研究、纳入受试者总数84万余人的荟萃分析显示，饮食中单不饱和脂肪酸多的人，心血管疾病发作事件少，死亡风险低。另一方面，单不饱和脂肪酸的耐热性也还可以。很多人认为橄榄油只能凉拌，其实，橄榄油拿来炒菜也是没问题的。只不过是特级初榨橄榄油中的一些抗氧化成分容易被破坏，但是这已经算是很小的缺点了。</p> \n" +
                    "  <p>其他单不饱和脂肪酸占比高的油还包括菜油（油茶籽油、山茶籽油），小众的牛油果油以及经过高油酸强化的油。</p> \n" +
                    "  <p>高油酸改良油是指通过选育出高油酸的油料作物品种榨出的食用油，比如高油酸花生油、高油酸葵花籽油中油酸占比被提高到75%，这比一般的花生油和葵花籽油高1倍多。消费者购买时要找靠得住的大牌子，还要看包装上的油酸含量。</p> \n" +
                    "  <p>双低菜籽油指的是低芥酸、低硫甙的菜籽油，因为油菜籽中这两样成分对健康有不利影响，从20世纪中开始各国都在积极培育双低油菜，从而榨出双低菜籽油。</p> \n" +
                    "  <p><strong>多不饱和脂肪酸</strong></p> \n" +
                    "  <p>多不饱和脂肪酸占比高的烹调油有一个很大的缺点就是不耐热，高温下一部分可以转为反式脂肪酸，还容易产生一系列有害物质甚至致癌物质，所以用法非常局限，只推荐用来凉拌或者是在蒸煮的食物上淋一些。那么这种油还值得吃吗？那就要看是n-3多还是n-6多。</p> \n" +
                    "  <p>n-6过剩，n-3稀缺。在多不饱和脂肪酸中，有生物学意义的是n-3和n-6系列。食物中最常见的n-6系列脂肪酸——亚油酸，虽然也是人体必需脂肪酸，但在它在各种肉类、豆类、坚果种子、谷物粮食等食物的油脂部分中都广泛存在且占比不低。因此正常吃饭，不会缺乏亚油酸。相比之下，n-3系列则稀缺得多。工业化时代，尤其是西式饮食模式中n-6急剧上升，n-3急剧减少，这个比值下降到了1:15甚至1:20。这种失衡的比例被科学家认为是导致慢性炎症和诸多慢性病风险上升的因素之一。</p> \n" +
                    "  <p>高n-6且不耐热的油别总吃。人体从饭菜中本身就能获取很多n-6，烹饪油再用n-6高的，n-6和n-3比例就更失调了。</p> \n" +
                    "  <p>补充n-3的烹调油。n-3家族中，直接补充EPA、DHA的健康效应更明显，食用油中不能直接吃到EPA、DHA，但亚麻籽油、紫苏籽油这些小众植物油有亮眼的α-亚麻酸（ALA）含量。</p> \n" +
                    "  <p>除了这些小众植物油，大豆油、核桃油虽然也有约7%—10%的α-亚麻酸，但亚油酸更多，在一半以上，考虑到竞争转化，可能达不到补充n-3的健康效应。</p> \n" +
                    "  <p>值得注意的是，高温烹饪会导致α-亚麻酸含量损失，亚麻籽油、紫苏籽油这些不耐热的油只推荐用于凉拌、炖煮，不能用来煎炸。</p> \n" +
                    "  <p>常见的花生油中，单不饱和脂肪酸占比44.5%，亚油酸占比34.3%，α-亚麻酸非常少，总体来说热稳定性好，可以用于炒菜，但也应和别的油换着吃。</p> \n" +
                    " </article>"
        )
    )
        private set

    private val htmlHeader = """
        <!DOCTYPE html>
        <html lang="en>
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title></title>
            <style>
                img{
                    max-width: 100% !important;
                }
            </style>
        </head>
        <body>
    """.trimIndent()

    private  val htmlFooter = """
        </body>
        </html>
    """.trimIndent()

    var content by mutableStateOf(
        """"""
    )

    suspend fun fetchInfo(selected: Int) {
        content =
        """
        $htmlHeader
        <h2>${list[selected].title}</h2>
        ${list[selected].webview}
        $htmlFooter
        """.trimIndent()
    }

    suspend fun fetchInfo_swiper(selected: Int) {
        content =
            """
        $htmlHeader
        <h2>${swiperData[selected-6].title}</h2>
        ${swiperData[selected-6].webview}
        $htmlFooter
        """.trimIndent()
    }

    suspend fun fetchInfo_zhuanlandata(selected: Int) {
        content =
            """
        $htmlHeader
        <h2>${zhuanlanData[selected].title}</h2>
        ${zhuanlanData[selected].webview}
        $htmlFooter
        """.trimIndent()
    }
}