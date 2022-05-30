package com.app.figure.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.vnpayReturn
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class payViewModel : ViewModel() {

    lateinit var getDataOrder : MutableLiveData<String>

    lateinit var vnPayReturn : MutableLiveData<String>

    init {
        getDataOrder = MutableLiveData()
        vnPayReturn = MutableLiveData()
    }

    fun getDataOrders() : MutableLiveData<String> {
        return getDataOrder
    }

    fun getVnPayReturns() : MutableLiveData<String> {
        return vnPayReturn
    }


    fun ApiTest(data : String) {
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getTestPay(data)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                getDataOrder.postValue(response.body())
                Log.e("rs",response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                getDataOrder.postValue(null)
                Log.e("fail", t.toString())
            }

        })
    }

    fun dataReturn(data : String) {
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.confimVnPay(data)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful){
                    vnPayReturn.postValue(response.body())
                    Log.e("rs",response.body().toString())
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                vnPayReturn.postValue(null)
                Log.e("fail", t.toString())
            }

        })
    }


}