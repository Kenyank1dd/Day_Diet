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
WEIGHT={'score1':0.1,'score2':0.1,'score3':0.1,'score4':0.1,'score5':0.1,'score6':0.1}
#权重之和应该为1
#TODO:
# 1.现在用户只有其所喜爱味道的属性，后期可以加上其不喜欢的味道。多一个属性
# 2.加菜谱数量，现在只有460分菜谱，比较少
# 3.用户种类现在只有5个，后期想加上美食杰上的更多标签
if __name__ == '__main__':
#     test_input_ingredients_list = [
#     "牛肉",
#     "番茄",
#     "土豆",
#     "猪肉",
#     "鸡肉",
#     "西兰花",
#     "辣椒",
#     "黄瓜"
# ]
#     # height weight 运动量 -> 糖、热量、脂肪
#     # 系统时间 午饭
#     # 晚饭 -> 剩余
#     # 剩余热量=（身高+体重）+运动量-已经吃的
#     # 早 20%剩余-> 脂肪糖分（标准）

#     #https://zhuanlan.zhihu.com/p/352590130
#     #营养:：糖、热量、脂肪
#     #用户标签:高血脂人群、高血压人群、高血糖人群、减肥人群、儿童
#     #口味:['酸辣味', '酸甜味', '家常味', '酱香味', '香辣味', '咸鲜味', '甜味', '鱼香味', '麻辣味',  '咖喱味', '茄汁味', '豆瓣味', '黑椒味', '蒜香味', '葱香味', '果味', '椒麻味','五香味', '奶香味','其它口味']

#     test_user1 = User(need_nutrition=[], kouwei=[15],category=[],allergen=[])
#     test_user2 = User(need_nutrition=[1, 3, 0.1], kouwei=[],category=[],allergen=[])
#     test_user3 = User(need_nutrition=[5, 1, 0.1], kouwei=[15],category=[0],allergen=[])
#     test_user_list = [test_user1, test_user2, test_user3]



#     test_family = Family(test_user_list,weight_dic=WEIGHT)
#     rec = DayDietRec('final.csv', 'model_cbow.bin')
#     start_time = time.time()
#     print(rec.get_topn_meals(test_input_ingredients_list, test_family, n=3))
#     end_time = time.time()
    # print(f'cost: {end_time-start_time}s')

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

    # height weight 运动量 -> 糖、热量、脂肪
    # 系统时间 午饭
    # 晚饭 -> 剩余
    # 剩余热量=（身高+体重）+运动量-已经吃的
    # 早 20%剩余-> 脂肪糖分（标准）

    #https://zhuanlan.zhihu.com/p/352590130
    #营养:：糖、热量、脂肪
    #用户标签:高血脂人群、高血压人群、高血糖人群、减肥人群、儿童
    #口味:['酸辣味', '酸甜味', '家常味', '酱香味', '香辣味', '咸鲜味', '甜味', '鱼香味', '麻辣味',  '咖喱味', '茄汁味', '豆瓣味', '黑椒味', '蒜香味', '葱香味', '果味', '椒麻味','五香味', '奶香味','其它口味']

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
    rec = DayDietRec('D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend\\final.csv', 'D:\\Desktop\\OPPOcode\\Day_Diet\\diet\\src\\main\\resources\\static\\daydietrecommend\\model_cbow.bin')
#     rec = DayDietRec('/root/daydietrecommend/final.csv', '/root/daydietrecommend/model_cbow.bin')
    res = rec.get_topn_meals(input_ingredients_list, test_family, n=3, recipe_num=recipe_num)
    for i in range(3):
        print(res.at[i, 'name'])

    for i in range(3):
        print(res.at[i, 'final_score'])
        print(res.at[i, 'score1'])
        print(res.at[i, 'score2'])
        print(res.at[i, 'score3'])
        print(res.at[i, 'score4'])
        print(res.at[i, 'score5'])
        print(res.at[i, 'score6'])
