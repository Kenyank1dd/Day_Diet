from classhelper import User,Family,DayDietRec
import time
import sys
import io
#1.套餐食材之间的相似度
#2.套餐食材与输入食材相似度
#3.套餐营养与用户所需营养的相似度
#4.套餐菜谱与用户类别的契合度
#5.套餐食材与时令的契合度
#6.套餐菜谱味道与用户所喜爱味道的契合度
WEIGHT={'score1':5,'score2':5,'score3':5,'score4':5,'score5':5,'score6':5}
#权重之和应该为1
#TODO:
# 1.现在用户只有其所喜爱味道的属性，后期可以加上其不喜欢的味道。多一个属性
# 2.加菜谱数量，现在只有460份菜谱，比较少
# 3.用户种类现在只有5个，后期想加上美食杰上的更多标签


if __name__ == '__main__':
    test_input_ingredients_list = ['猪肉','鱼','牛肉','番茄','土豆']
    # height weight 运动量 -> 糖、热量、脂肪
    # 系统时间 午饭 
    # 晚饭 -> 剩余
    # 剩余热量=（身高+体重）+运动量-已经吃的
    # 早 20%剩余-> 脂肪糖分（标准）
    
    #https://zhuanlan.zhihu.com/p/352590130
    #营养:：糖、热量、脂肪
    #用户标签:高血脂人群、高血压人群、高血糖人群、减肥人群、儿童
    #用户标签：['前列腺疾病' '糖尿病' '高血压' '高血脂' '冠心病' '中风' '消化性溃疡' '肠炎' '防癌抗癌' '胆石症' '肝硬化' '肾炎'
 #'痛风' '麻疹' '结核病' '肝炎' '动脉硬化' '甲状腺疾病' '胃炎' '贫血' '痔疮疾病' '月经不调' '子宫脱垂' '痛经' '更年期'
 #'小儿遗尿' '营养不良' '咽炎' '关节炎' '跌打骨折损伤' '骨质疏松' '耳鸣' '肺气肿' '口腔溃疡' '尿路结石' '支气管炎'
 #'术后']
    #口味:['酸辣味', '酸甜味', '家常味', '酱香味', '香辣味', '咸鲜味', '甜味', '鱼香味', '麻辣味',  '咖喱味', '茄汁味', '豆瓣味', '黑椒味', '蒜香味', '葱香味', '果味', '椒麻味','五香味', '奶香味','其它口味']
    
    test_user1 = User(need_nutrition=[10,1,2], kouwei=[6],category=[7],allergen=[])
    test_user2 = User(need_nutrition=[5,12,3], kouwei=[6],category=[7],allergen=[])
    test_user3 = User(need_nutrition=[2,3,14], kouwei=[6],category=[7],allergen=[])
    test_user_list = [test_user1, test_user2, test_user3]
    test_family = Family(test_user_list,weight_dic=WEIGHT)


    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
    index = 1

    recipe_num = int(sys.argv[index])
    index = index + 1
    weight2 = float(sys.argv[index])
    index = index + 1
    weight3 = float(sys.argv[index])
    index = index + 1
    weight4 = float(sys.argv[index])
    index = index + 1
    weight5 = float(sys.argv[index])
    index = index + 1
    weight6 = float(sys.argv[index])
    index = index + 1

    WEIGHT['score2'] = weight2
    WEIGHT['score3'] = weight3
    WEIGHT['score4'] = weight4
    WEIGHT['score5'] = weight5
    WEIGHT['score6'] = weight6

    usernum = int(sys.argv[index])
    index = index + 1
    ingrednum = int(sys.argv[index])
    index = index + 1
    input_ingredients_list = []
    user_list = []
    for i in range(ingrednum):
        input_ingredients_list.append(sys.argv[index])
        index = index + 1

    for i in range(usernum):
        nutrition = []
        allergen = []
        label = []
        taste = []
        for j in range(3):
            nutrition.append(float(sys.argv[index]))
            index = index + 1
        allergennum = int(sys.argv[index])
        index = index + 1
        for j in range(allergennum):
            allergen.append(sys.argv[index])
            index = index + 1
        labelnum = int(sys.argv[index])
        index = index + 1
        for j in range(labelnum):
            label.append(int(sys.argv[index]))
            index = index + 1
        tastenum = int(sys.argv[index])
        index = index + 1
        for j in range(tastenum):
            taste.append(int(sys.argv[index]))
            index = index + 1
        tempuser = User(need_nutrition=nutrition,kouwei=taste,category=label,allergen=allergen)
        user_list.append(tempuser)

    test_family = Family(user_list,weight_dic=WEIGHT)
#     rec = DayDietRec('D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend_new\\temp.csv', 'D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend_new\\model_cbow.bin')
    rec = DayDietRec('/root/daydietrecommend/temp.csv', '/root/daydietrecommend/model_cbow.bin')
    res = rec.get_topn_meals(input_ingredients_list, test_family, n=3, recipe_num=recipe_num)
    for i in range(3):
        print(res.at[i, 'name'])

    for i in range(3):
        print(res.at[i, 'describe'])

    for i in range(3):
        print(res.at[i, 'final_score'])
        print(res.at[i, 'score1'])
        print(res.at[i, 'score2'])
        print(res.at[i, 'score3'])
        print(res.at[i, 'score4'])
        print(res.at[i, 'score5'])
        print(res.at[i, 'score6'])

