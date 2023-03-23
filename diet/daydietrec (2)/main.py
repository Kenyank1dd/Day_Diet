from classhelper import user,family,DayDietRec
if __name__=='__main__':
    test_input_ingredients_list=['牛肉','番茄','土豆','猪肉','鸡肉','西兰花','辣椒','黄瓜']
    test_user1=user(need_nutrition=[10,200,30],allergen=['土豆'])
    test_user2=user(need_nutrition=[10,300,30],allergen=['螃蟹'])
    test_user3=user(need_nutrition=[5,100,10],allergen=['包菜'])
    #test_user4=user(need_nutrition=[5,500,10],allergen=['包菜'])
    test_user_list=[test_user1,test_user2,test_user3]
    test_family=family(test_user_list)

    rec=DayDietRec('final_recipes.csv','model_cbow.bin')
    print(rec.get_topn_meals(test_input_ingredients_list,test_family,n=50))
   
