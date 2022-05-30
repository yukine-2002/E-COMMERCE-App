package com.app.figure.retrofit

import com.app.figure.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("cateProduct")
    fun getProductList() : Call<List<productModels>>


    @GET("topBuyProduct")
    fun getProductListTopBuy() : Call<List<productModels>>


    @GET("getTopFigureAttention")
    fun getTopFigureAttention() : Call<List<productModels>>


    @GET("getCate/{id}")
    fun getCate(@Query("id") id : String) : Call<cateModel>


    @POST("Login")
    fun Login(@Query("username") username : String, @Query("password") password : String) : Call<responeLogin>


    @POST("register")
    fun register(@Query("username") username : String, @Query("password") password : String) : Call<ResponseBody>

    @GET("getInfoUser/{id}")
    fun getUser(@Query("id") id : String) : Call<person>

    @GET("getComment/{id}")
    fun getComment(@Query("id") id : String) : Call<List<commentModels>>


    @GET("getImg/{id}")
    fun getImg(@Query("id") id : String) : Call<List<imgProduct>>


    @GET(" getProduct")
    fun  getProduct() : Call<List<productModels>>

    @GET(" getBlog")
    fun  getBlog() : Call<List<BlogModel>>

    @GET(" getAllBlog")
    fun  getAllBlog() : Call<List<BlogModel>>

    @GET("getCommentBlog/{id}")
    fun  getCommentBlog(@Query("id") id : String) : Call<List<BlogCommentModel>>

    @GET(" logout")
    fun  LogOut() : Call<ResponseBody>

    @GET("getAllCate")
    fun  getAllCate() : Call<List<cateModel>>

    @GET("getProductByCate/{id}")
    fun  getProductByCate(@Query("id") id : String) : Call<List<productModels>>

    @GET("ApiPays/{data}")
    fun  getTestPay(@Query("data") data : String) : Call<String>

    @GET("vnPayReturn")
    fun  vnPayReturn() : Call<String>

    @GET("confimVnPay/{data}")
    fun  confimVnPay(@Query("data") data : String) : Call<String>

    @GET("updateCurrent")
    fun  updateCurrent(@Query("action") action : String,@Query("value") value : String,@Query("id") id : String) : Call<String>

    @Multipart
    @POST("UpdateImage")
    fun UpdateImage(@Part("id") id : Int, @Part img : MultipartBody.Part) : Call<String>


    @GET("getListOrderById")
    fun  getListOrderById(@Query("id") id : String) : Call<ArrayList<getDataOrderModel>>

    @GET("commentDetailProduct/{id_pro}/{content}/{id_user}")
    fun commentDetailProduct(@Query("id_pro") id : Int,@Query("content") content : String,@Query("id_user") id_user : String) : Call<String>

    @POST("checkUser/{data}")
    fun checkUser(@Query("data") data : String) : Call<responeLogin>

    @GET("searchProduct/{name}")
    fun searchProduct(@Query("name") name : String) : Call<List<productModels>>

    @GET("addFavProduct")
    fun addFavProduct(@Query("id_user") id_user : String,@Query("id_pro") id_pro : String) : Call<String>

    @GET("getFavProduct")
    fun getFavProduct(@Query("id_user") id_user : String) : Call<List<productModels>>

    @GET("RateProduct")
    fun RateProduct(@Query("id_pro") id_pro : String,@Query("index") index : String,@Query("id_user") id_user : String) : Call<String>

}