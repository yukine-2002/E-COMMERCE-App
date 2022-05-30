package com.app.figure.viewmodel

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.*
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class loginViewModel : ViewModel() {

    lateinit var user : MutableLiveData<userModel>
    lateinit var responeLogins: MutableLiveData<responeLogin>
    lateinit var person: MutableLiveData<person>
    lateinit var loginWithGoogle : MutableLiveData<responeLogin>

    init {
        user =MutableLiveData()
        responeLogins = MutableLiveData()
        person = MutableLiveData()
        loginWithGoogle = MutableLiveData()
    }

    fun getUsers() : MutableLiveData<userModel>{
        return user
    }


    fun getResponseLogins () : MutableLiveData<responeLogin>{
        return responeLogins
    }


    fun getPersons () : MutableLiveData<person>{
        return person
    }

    fun GetLoginWithGoogle () : MutableLiveData<responeLogin>{
        return loginWithGoogle
    }


    fun ApiRegister (username : String , password : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroservice = retroInstance.create(RetroServiceInterface::class.java)
        retroservice.register(username,password)
    }

    fun ApigetInfoUser(id : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)



        var call = retroService.getUser(id)

        call.enqueue(object : Callback<person> {
            override fun onResponse(call: Call<person>, response: Response<person>) {
                Log.e("check user : " ,response.body().toString())
                person.postValue(response.body())
            }
            override fun onFailure(call: Call<person>, t: Throwable) {
                person.postValue(null)
            }
        })
    }

    fun ApiCheck(data : String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.checkUser(data)
        call.enqueue(object  : Callback<responeLogin> {
            override fun onResponse(call: Call<responeLogin>, response: Response<responeLogin>) {
               loginWithGoogle.postValue(response.body())
            }

            override fun onFailure(call: Call<responeLogin>, t: Throwable) {
                Log.w("fail " , t.toString())
            }

        })
    }


}