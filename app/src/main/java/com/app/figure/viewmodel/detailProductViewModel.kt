package com.app.figure.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.cateModel
import com.app.figure.Model.commentModels
import com.app.figure.Model.imgProduct
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detailProductViewModel : ViewModel() {

    lateinit var getCate : MutableLiveData<cateModel>
    lateinit var getImage : MutableLiveData<List<imgProduct>>
    lateinit var getComment : MutableLiveData<List<commentModels>>

    init {
        getCate = MutableLiveData()
        getComment = MutableLiveData()
        getImage= MutableLiveData()
    }

    fun getImages() : MutableLiveData<List<imgProduct>>{
        return  getImage
    }

    fun getCate() : MutableLiveData<cateModel>{
        return  getCate
    }

    fun getComments() : MutableLiveData<List<commentModels>>{
        return  getComment
    }

    fun ApiCate(id : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getCate(id)
        call.enqueue(object : Callback<cateModel> {
            override fun onResponse(call: Call<cateModel>, response: Response<cateModel>) {
                getCate.postValue(response.body())
            }

            override fun onFailure(call: Call<cateModel>, t: Throwable) {
                getCate.postValue(null)
            }

        })

    }

    fun ApiComment(id : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getComment(id)
        call.enqueue(object : Callback<List<commentModels>> {
            override fun onResponse(call: Call<List<commentModels>>, response: Response<List<commentModels>>) {
                getComment.postValue(response.body())
            }

            override fun onFailure(call: Call<List<commentModels>>, t: Throwable) {
                getComment.postValue(null)
            }

        })

    }

    fun PutComment(id:Int,content:String,id_user:String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.commentDetailProduct(id,content,id_user)
        call.enqueue(object  : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("check",response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("fail",t.toString())
            }

        })
    }


    fun ApigetImage(id : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getImg(id)
        call.enqueue(object : Callback<List<imgProduct>> {
            override fun onResponse(call: Call<List<imgProduct>>, response: Response<List<imgProduct>>) {
                getImage.postValue(response.body())
            }

            override fun onFailure(call: Call<List<imgProduct>>, t: Throwable) {
                getImage.postValue(null)
            }

        })

    }


}