package com.app.figure.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.getDataOrderModel
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragmentUserViewModel : ViewModel() {

    lateinit var updateUser : MutableLiveData<String>
    lateinit var getDataOrder : MutableLiveData<ArrayList<getDataOrderModel>>

     init{
         updateUser = MutableLiveData()
         getDataOrder = MutableLiveData()
     }
    fun getStatusupdateUser() : MutableLiveData<String>{
        return updateUser
    }

    fun getDataOrders() : MutableLiveData<ArrayList<getDataOrderModel>>{
        return getDataOrder
    }

    fun ApiUpdateUser (action : String,value:String,id :String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroServiec = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroServiec.updateCurrent(action,value,id)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                updateUser.postValue(response.body())
                Log.e("check",response.body().toString());
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                updateUser.postValue(null)
                Log.e("error",t.toString());
            }

        })
    }

    fun ApiCallDataOrder(id :String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroServiec = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroServiec.getListOrderById(id)
        call.enqueue(object  : Callback<ArrayList<getDataOrderModel>> {
            override fun onResponse(
                call: Call<ArrayList<getDataOrderModel>>,
                response: Response<ArrayList<getDataOrderModel>>
            ) {
                getDataOrder.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<getDataOrderModel>>, t: Throwable) {
                getDataOrder.postValue(null)
            }

        })
    }

}