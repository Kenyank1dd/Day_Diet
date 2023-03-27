from sklearn.feature_extraction.text import TfidfVectorizer
from collections import defaultdict
import numpy as np
import pandas as pd
import sys
import string
from itertools import combinations
from gensim.models import Word2Vec
from sklearn.metrics.pairwise import cosine_similarity

class TfidfEmbeddingVectorizer(object):
    def __init__(self, model_cbow):
        self.model_cbow = model_cbow
        self.word_idf_weight = None
        self.vector_size = model_cbow.wv.vector_size
    def fit(self, docs): 
        """
            建立一个tfidf模型，计算每个词的idf值作为其权重。
        """
        text_docs = []
        for doc in docs:
            text_docs.append(" ".join(doc))
        #print(text_docs)
        tfidf = TfidfVectorizer()
        tfidf.fit(text_docs)  
        # 如果一个单词从未见过，则给出已知idf值的最大值idf
        max_idf = max(tfidf.idf_)  
        self.word_idf_weight = defaultdict(
            lambda: max_idf,
            [(word, tfidf.idf_[i]) for word, i in tfidf.vocabulary_.items()],
        )
        #print(self.word_idf_weight)
        return self

    def transform(self, docs): 

        doc_word_vector = self.doc_average_list(docs)
        return doc_word_vector

    def doc_average(self, doc):
        """
            计算文档词嵌入的加权平均值
        """
        mean = []
        #print(doc)
        for word in doc:
            if word in self.model_cbow.wv.index_to_key:
                mean.append(
                    self.model_cbow.wv.get_vector(word) * self.word_idf_weight[word]
                ) 
        if not mean:  
            return np.zeros(self.vector_size)
        else:
            mean = np.array(mean).mean(axis=0)
            return mean
    def doc_average_list(self, docs):
        return np.vstack([self.doc_average(doc) for doc in docs])

class user: #user类
    def __init__(self,need_nutrition=[],allergen=[]):
        self.allergen=allergen
        self.need_nutrition=need_nutrition

class family:
    def __init__(self,user_list=[]):
        self.user_list=user_list
        self.user_num=len(self.user_list)


class DayDietRec:
    def __init__(self,data_path,model_save_path):
        self.data=pd.read_csv(data_path)
        self.corpus = self.get_and_sort_corpus_ingredient(self.data)
        self.train_ingredient_model(model_save_path)
        self.model=self.load_ingredient_model(model_save_path)

    def get_ingredient(self,s):
        res=s.split(' ')
        if '' in res:
            res.remove('')
        return res
    def get_and_sort_corpus_ingredient(self,data):
        '''
            data是一个csv文件
        '''
        corpus_sorted = []
        for doc in data.parsed:
            doc=self.get_ingredient(doc)  #doc是一个食材列表
            doc.sort()
            corpus_sorted.append(doc)
        return corpus_sorted

    def get_window(self,corpus):# 计算每个文档的平均长度
        lengths = [len(doc) for doc in corpus]
        avg_len = float(sum(lengths)) / len(lengths)
        return round(avg_len)

    def train_ingredient_model(self,save_path):
        model_cbow = Word2Vec(
            self.corpus, sg=0, workers=8, window=self.get_window(self.corpus), min_count=1, vector_size=50
        )
        model_cbow.save(save_path)

    def load_ingredient_model(self,model_path):
        model = Word2Vec.load(model_path)
        # 标准化嵌入
        #model.init_sims(replace=True)
        if model:
            pass
        tfidf_vec_trr = TfidfEmbeddingVectorizer(model)
        tfidf_vec_trr.fit(self.corpus)
        return tfidf_vec_trr
        
    def recipe_similarity(self,recipe1,recipe2,tfidf_vec_model):
        emb1=tfidf_vec_model.transform([recipe1])[0].reshape(1,-1)
        emb2=tfidf_vec_model.transform([recipe2])[0].reshape(1,-1)
        similarity=cosine_similarity(emb1,emb2)
        return similarity

    def mean_similarity_in_meal(self,meal,tfidf_vec_model):
        sum_similarity=[]
        for i in range(len(meal)-1):
            for j in range(i+1,len(meal)):
                temp=self.recipe_similarity(meal[i],meal[j],tfidf_vec_model)
                sum_similarity.append(temp)
        return (np.array(sum_similarity).mean()+1)/2  #计算这个套餐的平均相似度,映射到0-1

    def get_nutrition_similarity(self,recipe_nutrition,user_need_nutrition):
        '''
            输入营养成分列表
        '''
        recipe_nutrition=np.array(recipe_nutrition)
        user_need_nutrition=np.array(user_need_nutrition)
        cos_sim = cosine_similarity(recipe_nutrition.reshape(1, -1), user_need_nutrition.reshape(1, -1))
        return cos_sim[0][0]
 
    def similarity_of_meal_and_ingredients(self,meal,ingredients_list,tfidf_vec_model):
        '''
            recipes_list:[['黄瓜','番茄'],['土豆','牛肉'],['猪肉','白菜'],['鸡蛋','番茄']] 
            ingredient_list:['牛肉','番茄','土豆','白菜','鱼','猪肉','胡萝卜','鸡蛋','黄瓜'] 
        '''
        sum_similarity=[]
        for r in meal:
            sum_similarity.append(self.recipe_similarity(r,ingredients_list,tfidf_vec_model))
        return (np.array(sum_similarity).mean()+1)/2   #映射到0-1
        
    def get_meal_score(self,it,ingredients_list,the_family,tfidf_vec_model):
        user_need_nutrition=[0,0,0] #糖，热量，脂肪
        allergen=[]
        for u in the_family.user_list:  #将所有家庭成员的过敏原汇总
            allergen.extend(u.allergen)
            user_need_nutrition[0]+=u.need_nutrition[0]  #计算家庭成员所需的营养
            user_need_nutrition[1]+=u.need_nutrition[1]
            user_need_nutrition[2]+=u.need_nutrition[2]
        meal_nutrition=[0,0,0] #糖，热量，脂肪
        
        meal=[]
        for index in it:
            recipe_ingredient=self.get_ingredient(self.data['parsed'][index])
            meal.append(recipe_ingredient) #将食材加入到这顿meal中
            meal_nutrition[0]+=float(self.data['tang'][index][:-1])
            meal_nutrition[1]+=float(self.data['reliang'][index][:-1])
            meal_nutrition[2]+=float(self.data['zhifang'][index][:-1])
            # 将食材中的营养相加
        for r in meal:  #判断meal有没有用户过敏的食材，若有，直接0分
            for ing in r:
                if ing in allergen:
                    return 0
        
        meal_score1=self.mean_similarity_in_meal(meal,tfidf_vec_model)  # 做的菜中的平均相似度，越小越好
        meal_score2=self.similarity_of_meal_and_ingredients(meal,ingredients_list,tfidf_vec_model) #做的菜和原料的相似度，越高越好
        meal_score3=self.get_nutrition_similarity(meal_nutrition,user_need_nutrition) #计算用户所需营养和meal中的营养相似度

        return meal_score2*meal_score3/meal_score1
    def get_topn_meals(self,ingredients,the_family,n=5):
        '''
            Parameters:
                data:最终菜谱csv
                ingredients:原料列表
                the_family:用户家庭信息
                tfidf_vec_model:推荐模型
                n:n表示返回前几个菜谱
            Returns:
                获得前n个菜谱推荐
        '''
        search_num=100 #搜索次数
        search_recipes_num=the_family.user_num*2
        scores=[]
        for i in range(search_num):
            search_from=np.random.choice(len(self.data),search_recipes_num,replace=False)  #从所有菜谱中取一些菜谱
            itering=combinations(search_from,the_family.user_num)  #获得这些菜谱其中的所有组合
            itering=list(itering)
            for it in itering: # it:(120,3,45)
                score=self.get_meal_score(it,ingredients,the_family,self.model)
                scores.append((it,score))   

        scores.sort(key=lambda x:x[1],reverse=True)
        scores=scores[:n]
        
        res=pd.DataFrame(columns=['name','score'])
        count=0
        for score in scores:
            name=''
            for index in score[0]:
                name+=self.data['name'][index]+' '
            res.at[count,'name']=name
            res.at[count,'score']=score[1]
            count+=1
        return res

if __name__=='__main__':
    test_input_ingredients_list=['牛肉','番茄','土豆','猪肉','鸡肉','西兰花','辣椒','黄瓜']
    test_user1=user(need_nutrition=[10,200,30],allergen=['土豆'])
    test_user2=user(need_nutrition=[10,300,30],allergen=['螃蟹'])
    test_user3=user(need_nutrition=[5,100,10],allergen=['包菜'])
    test_user_list=[test_user1,test_user2,test_user3]
    test_family=family(test_user_list)
    input_ingredients_list = []
#     print(sys.argv)
    usernum = int(sys.argv[1])
    ingrednum = int(sys.argv[2])
    allergenidx = 3 + ingrednum + 3 * usernum
    user_list = []
    for i in range(ingrednum):
        input_ingredients_list.append(sys.argv[3+i])

    for i in range(usernum):
        nutrition = []
        allergen = []
        for j in range(3):
            nutrition.append(int(sys.argv[3+ingrednum+j+3*i]))
        allergennum = int(sys.argv[allergenidx])
        for j in range(1,allergennum+1):
            allergen.append(sys.argv[allergenidx+j])
        allergenidx = allergenidx + allergennum + 1
        tempuser = user(need_nutrition=nutrition,allergen=allergen)
        user_list.append(tempuser)

    user_family = family(user_list)
    rec=DayDietRec('src/main/resources/static/final_recipes.csv',
                    'model_cbow.bin')
    res = rec.get_topn_meals(input_ingredients_list,user_family,n=50)
    for item in res['name']:
        print(item)