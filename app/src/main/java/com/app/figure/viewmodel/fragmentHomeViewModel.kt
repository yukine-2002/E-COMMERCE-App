package com.app.figure.viewmodel

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.BlogModel
import com.app.figure.Model.cateModel
import com.app.figure.Model.productModels
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class fragmentHomeViewModel : ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<productModels>>



    lateinit var DataListByCate : MutableLiveData<List<productModels>>


    lateinit var DataBlog : MutableLiveData<List<BlogModel>>

    lateinit var DataCate : MutableLiveData<List<cateModel>>

    init {
        liveDataList = MutableLiveData()


        DataListByCate = MutableLiveData()
        DataBlog = MutableLiveData()
        DataCate = MutableLiveData()

    }

    fun getDataListByCates(): MutableLiveData<List<productModels>> {
        return DataListByCate
    }


    fun getDataCates(): MutableLiveData<List<cateModel>> {
        return DataCate
    }

    fun getLiveDataObserver(): MutableLiveData<List<productModels>> {
        return liveDataList
    }




    fun getDataBlogs() :  MutableLiveData<List<BlogModel>> {
        return DataBlog
    }

    fun ApiCallGetCate(){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call =retroService.getAllCate()
        call.enqueue(object : Callback<List<cateModel>> {
            override fun onResponse(
                call: Call<List<cateModel>>,
                response: Response<List<cateModel>>
            ) {
                DataCate.postValue(response.body())
            }

            override fun onFailure(call: Call<List<cateModel>>, t: Throwable) {
                DataCate.postValue(null)
            }

        })
    }


    fun ApiCallProductTop() {
        var retroInstance = retroInstance.getRetroInstance()
        var retroservice = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroservice.getProductList()
        call.enqueue(object : Callback<List<productModels>> {
            override fun onResponse(call: Call<List<productModels>>, response: Response<List<productModels>>) {
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                print(t)
                liveDataList.postValue(null)
            }

        })
    }

    fun ApigetProductByCate(id : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroservice = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroservice.getProductByCate(id)
        call.enqueue(object  : Callback<List<productModels>> {
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
                DataListByCate.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                DataListByCate.postValue(null)
            }


        })

    }





    fun ApiCallDataBlog(){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getBlog()

        call.enqueue(object : Callback<List<BlogModel>>{
            override fun onResponse(
                call: Call<List<BlogModel>>,
                response: Response<List<BlogModel>>
            ) {
                DataBlog.postValue(response.body())
            }

            override fun onFailure(call: Call<List<BlogModel>>, t: Throwable) {
                DataBlog.postValue(null)
            }

        })

    }


}