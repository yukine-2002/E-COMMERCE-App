package com.app.figure.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.figure.Model.productModels
import com.app.figure.UI.Activity.PaySuccesActivity
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragmentProductViewModel: ViewModel() {

    lateinit var product : MutableLiveData<List<productModels>>
    lateinit var DataListTopBuy: MutableLiveData<List<productModels>>
    lateinit var DataListAttentionProduct : MutableLiveData<List<productModels>>
    lateinit var dataSearchProducct : MutableLiveData<List<productModels>>
    lateinit var dataFavProduct : MutableLiveData<List<productModels>>
    lateinit var dataRaiting : MutableLiveData<String>
    init {
        product = MutableLiveData()
        DataListAttentionProduct = MutableLiveData()
        DataListTopBuy= MutableLiveData()
        dataSearchProducct = MutableLiveData()
        dataFavProduct = MutableLiveData()
        dataRaiting = MutableLiveData()
    }

    fun getProducts() :  MutableLiveData<List<productModels>> {
        return product
    }

    fun getDataListAttentionProductObserver(): MutableLiveData<List<productModels>> {
        return DataListAttentionProduct
    }

    fun getDataTopBuyObserver(): MutableLiveData<List<productModels>> {
        return DataListTopBuy
    }

    fun getDataSearchProduct () : MutableLiveData<List<productModels>>{
        return dataSearchProducct
    }

    fun getDataFavProducts() : MutableLiveData<List<productModels>>{
        return dataFavProduct;
    }

    fun getDataRaitings() : MutableLiveData<String>{
        return dataRaiting;
    }

    fun  ApiCallProductAttentions(){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getTopFigureAttention()
        call.enqueue(object : Callback<List<productModels>>{
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
                DataListAttentionProduct.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                DataListAttentionProduct.postValue(null)
            }

        })
    }

    fun ApiCallProductBuys(){
        var retroInstance = retroInstance.getRetroInstance()

        var retroService = retroInstance.create(RetroServiceInterface::class.java)

        var call = retroService.getProductListTopBuy()


        call.enqueue(object : Callback<List<productModels>> {
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
                DataListTopBuy.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                print(t)
                DataListTopBuy.postValue(null)
            }

        })
    }

    fun ApigetAllProduct(){

        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getProduct()

        call.enqueue(object : Callback<List<productModels>> {
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
                product.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                product.postValue(null)
            }

        })

    }

    fun  ApiSearchProduct(name:String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.searchProduct(name)
        call.enqueue(object  : Callback<List<productModels>>{
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
               dataSearchProducct.postValue(response.body())
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                dataSearchProducct.postValue(null)
                Log.e("fail",t.toString())

            }
        })
    }

    fun ApiFavProduct(id_user:String){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.getFavProduct(id_user)
        call.enqueue(object : Callback<List<productModels>>{
            override fun onResponse(
                call: Call<List<productModels>>,
                response: Response<List<productModels>>
            ) {
                if(response.isSuccessful){
                    dataFavProduct.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<productModels>>, t: Throwable) {
                Log.e("fail",t.toString())
            }

        })
    }

    fun addFavProduct(id_user : String , id_pro : String,context : Context){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.addFavProduct(id_user,id_pro)
        call.enqueue(object  : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.isSuccessful){
                    if(response.body()?.toInt() == 201 ){
                        var alert: AlertDialog = AlertDialog.Builder(context).create()
                        alert.setTitle("Thanh cong")
                        alert.setMessage("Thêm sản phẩm yêu thích thành công")
                        alert.setButton(Dialog.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener {

                                dialog, which -> dialog.dismiss()
                        })
                        alert.show()
                    }
                    if(response.body()?.toInt() == 401){
                        var alert: AlertDialog = AlertDialog.Builder(context).create()
                        alert.setTitle("That bai")
                        alert.setMessage("sản phẩm đã tồn tại trng mục yêu thích")
                        alert.setButton(Dialog.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener {

                                dialog, which -> dialog.dismiss()
                        })
                        alert.show()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("fail",t.toString())
            }

        })
    }
    fun RateProduct(id_pro : String,index:String,id_user : String ){
        var retroInstance = retroInstance.getRetroInstance()
        var retroService = retroInstance.create(RetroServiceInterface::class.java)
        var call = retroService.RateProduct(id_pro,index,id_user)
        call.enqueue(object  : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    dataRaiting.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
               dataRaiting.postValue(null)
                Log.e("fail",t.toString())
            }
        })

    }

}