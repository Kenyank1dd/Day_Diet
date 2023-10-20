package com.example.daydiet.service

import com.example.daydiet.model.Domain.FamilyInfoResponse
import com.example.daydiet.model.Domain.WeightResponse
import com.example.daydiet.model.entity.*
import retrofit2.http.*

interface Homeservice {

//    @FormUrlEncoded 和@POST@Field一起用    @GET和@Query一起用


    @GET(Common.Get_Pyq)
    suspend fun pyqmessage(
        @Query("user_id") user_id :String
    ):PyqmessageResponse

    @POST(Common.Register)
    suspend fun register(  //注册
        @Body user : User
    ):BaseResponse

    @GET(Common.Login)
    suspend fun login(    //登录
        @Query("user_phone") user_phone: String,
        @Query("user_password") user_password : String
    ):UserResponse     //返回的response中会有一个token

    @POST(Common.Recommend_Recipe)
    suspend fun Recommend_Recipe(
        @Body ings : SimpleString,
        @Header("token") token : String
    ) : RecipeResponse

    @POST(Common.Identify_recipe)
    suspend fun IdentifyRecipe(
        @Body diet_picture : String,
        @Header("token") token : String
    ) : RecipeResponse

    @GET(Common.Search_Recipe)
    suspend fun Search_Recipe(
        @Query("searchtxt") rec_name : String
    ) : RecipeResponse

    @GET(Common.Rank_Recipe)
    suspend fun Rank_Recipe() : RecipeResponse

    @GET(Common.Generate_Picture)
    suspend fun Generate_Picture(
        @Query("rec_name") rec_name : String
    ) : SimpleStringResponse

    @GET(Common.Generate_Recipe)
    suspend fun Generate_Recipe(@Body ings_names : List<String>) : SimpleStringResponse

    @GET(Common.Record_Water)
    suspend fun Record_Water(
//        @Field("token") token : String,
        @Header("token") token :String
    ) : SimpleNumberResponse

    @PUT(Common.Update_Water)
    suspend fun Update_Water(
        @Field("water_num") waternum : Int,
//        @Field("token") token : String
        @Header("token") token :String
    ) : SimpleNumberResponse

    @GET(Common.Get_Family)
    suspend fun Get_Family(
//        @Field("token") token : String
        @Header("token") token :String
    ) : FamilyResponse


    @GET(Common.Get_Diet)
    suspend fun Get_Diet(
        @Query("date") date : String,
//        @Field("token") token : String
        @Header("token") token :String
    ) : RecentDietResponse

    @GET(Common.Get_Weight)
    suspend fun Get_Weight(
//        @Field("token") token : String
        @Header("token") token :String
    ) : WeightResponse

    @GET(Common.Get_Community)
    suspend fun Get_Community(
//        @Field("token") token : String
        @Header("token") token :String
    ) : PostResponse

    @GET(Common.Community_Article)
    suspend fun Community_Article(
//        @Field("token") token : String
        @Header("token") token :String
    ) : ArticleResponse

    @GET(Common.Collect_Article)
    suspend fun Collect_Article(
//        @Field("token") token : String
        @Header("token") token :String
    ) : ArticleResponse

    @FormUrlEncoded
    @POST(Common.Add_Article)
    suspend fun Add_Article(
//        @Field("token") token : String,
        @Field("article_title") article_title : String,
        @Header("token") token :String
    ):BaseResponse

    @GET(Common.Update_RegisterInfo)
    suspend fun Update_RegisterInfo(
        @Body info: RegisterInfo,
        @Header("token") token :String
    ):RegisterInfo

    @GET(Common.Get_dis_all)
    suspend fun Get_dis_all(      //获取所有家庭成员
        @Header("token") token :String
    ):FamilyInfoResponse

    @GET(Common.Update_dis_all)
    suspend fun Update_dis_all(           //更新家庭成员
        @Header("token") token :String,
        @Query("family_relation") family_relation : String
    ):FamilyInfoResponse

    @GET(Common.Update_Weight)
    suspend fun Update_Weight(
        @Query("weight") weight :Long,
        @Header("token") token :String
    ): WeightResponse

    @GET(Common.Get_cal)
    suspend fun Get_cal(
        @Query("rec_name") rec_name : String
    ):SimpleNumberResponse



    companion object{
        fun instance():Homeservice{
            return Network.createService(Homeservice::class.java)
        }
    }

}