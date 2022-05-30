package com.app.figure.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.BlogCommentModel
import com.app.figure.Model.BlogModel
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragmentBlogViewModel : ViewModel() {

    lateinit var DataBlog : MutableLiveData<List<BlogModel>>
    lateinit var DataCommentBlog : MutableLiveData<List<BlogCommentModel>>

    init {
        DataBlog = MutableLiveData()
        DataCommentBlog = MutableLiveData()

    }

    fun getDataBlogs() : MutableLiveData<List<BlogModel>> {
        return DataBlog
    }

    fun getDataCommentBlogs() : MutableLiveData<List<BlogCommentModel>> {
        return DataCommentBlog
    }

    fun ApiCallCommentBlog(id : String) {
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getCommentBlog(id)
        call.enqueue(object : Callback<List<BlogCommentModel>> {
            override fun onResponse(
                call: Call<List<BlogCommentModel>>,
                response: Response<List<BlogCommentModel>>
            ) {
                DataCommentBlog.postValue(response.body())
            }

            override fun onFailure(call: Call<List<BlogCommentModel>>, t: Throwable) {
                DataCommentBlog.postValue(null)
            }

        })
    }

    fun ApiCallDataBlog(){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getAllBlog()

        call.enqueue(object : Callback<List<BlogModel>> {
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