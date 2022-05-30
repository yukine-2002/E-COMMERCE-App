package com.app.figure.UI.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.figure.Model.cartModel
import com.app.figure.Model.orderModel
import com.app.figure.Model.person
import com.app.figure.R
import com.app.figure.viewmodel.payViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PayActivity : AppCompatActivity() {
    private var check :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        backActivity()
       var total  = intent.getStringExtra("total").toString()
        setData(total)
        setPayNormal()
        setPayVNPay()
        setPayMoMo()
        Pay()


    }

    fun Pay(){
        var btnPay :Button = findViewById(R.id.actionCart)
        btnPay.setOnClickListener {
            if(this.check == 1){
                payNormal()
                Toast.makeText(this,"normal",Toast.LENGTH_SHORT).show()
            }
            if(this.check == 2){
                payVnpay()
                Toast.makeText(this,"vnpay",Toast.LENGTH_SHORT).show()
            }
            if(this.check == 3){
                payMomo()
                Toast.makeText(this,"momo",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
    fun setData(total : String){
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var getInfo = sharedPreferences.getString("infoUser","")

        if(!getInfo!!.isEmpty()){
            var name : EditText = findViewById(R.id.name)
            var sdt : EditText = findViewById(R.id.sdt)
            var cmnd : EditText = findViewById(R.id.cmd)
            var diachi : EditText = findViewById(R.id.diachi)
            var email : EditText = findViewById(R.id.email)
            var totals : TextView = findViewById(R.id.total)
            var totalMain : TextView = findViewById(R.id.totalMain)

            var gson = Gson()
            var getInfoUser : person = gson.fromJson(getInfo,person::class.java)

            name.text = Editable.Factory.getInstance().newEditable(getInfoUser.fullname)
            email .text = Editable.Factory.getInstance().newEditable(getInfoUser.email)
            cmnd.text = Editable.Factory.getInstance().newEditable(getInfoUser.cmnd)
            sdt.text = Editable.Factory.getInstance().newEditable("0"+getInfoUser.sdt)
            diachi.text = Editable.Factory.getInstance().newEditable(getInfoUser.adress)
            totalMain.text = (total.toInt() + (total.toInt() * 0.1)).toString()
             totals.text = total

        }
    }
    fun setPayNormal(){
        var normals : TextView = findViewById(R.id.normals)
        var momo : ImageView = findViewById(R.id.momo)
        var vnpay : ImageView = findViewById(R.id.vnpay)
        normals.setOnClickListener {
            this.check = 1
            normals.background.setTint(Color.parseColor("#03B4BE"))
            momo.background.setTint(Color.parseColor("#FFFFFF"))
            vnpay.background.setTint(Color.parseColor("#FFFFFF"))
        }
    }
    fun setPayVNPay(){
        var normals : TextView = findViewById(R.id.normals)
        var momo : ImageView = findViewById(R.id.momo)
        var vnpay : ImageView = findViewById(R.id.vnpay)
        vnpay.setOnClickListener {
            this.check = 2
            vnpay.background.setTint(Color.parseColor("#03B4BE"))
            momo.background.setTint(Color.parseColor("#FFFFFF"))
            normals.background.setTint(Color.parseColor("#FFFFFF"))
        }
    }
    fun setPayMoMo(){
        var normals : TextView = findViewById(R.id.normals)
        var momo : ImageView = findViewById(R.id.momo)
        var vnpay : ImageView = findViewById(R.id.vnpay)
        momo.setOnClickListener {
            this.check = 3
            momo.background.setTint(Color.parseColor("#03B4BE"))
            vnpay.background.setTint(Color.parseColor("#FFFFFF"))
            normals.background.setTint(Color.parseColor("#FFFFFF"))
        }
    }
    fun payNormal(){
            var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
            var getInfo = sharedPreferences.getString("infoUser","")
            var gson = Gson()

            var sharedPreference = getSharedPreferences("data_cart", Context.MODE_PRIVATE)!!
            var cart = sharedPreference.getString("cart", "")
            var item = object : TypeToken<ArrayList<cartModel>>() {}.type
            var getCart: ArrayList<cartModel> = gson.fromJson(cart, item)!!

            var totalMain : TextView = findViewById(R.id.totalMain)

            var getInfoUser : person = gson.fromJson(getInfo,person::class.java)

            var getOrder = orderModel(totalMain.text.toString(),getCart,getInfoUser.id_per,12,"giaohang","")

            var viewModel = ViewModelProvider(this).get(payViewModel::class.java)

            var  a =  MaterialAlertDialogBuilder(this)
            a.setMessage("Đang thực hiện thanh toán vui lòng đợi")
            a.show()

        viewModel.getDataOrders().observe(this, Observer<String> {
                if (it != null ){
                    Toast.makeText(this,"data : " + it,Toast.LENGTH_LONG).show()
                    if(it.toInt() == 201){
                        var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
                        sharedPreferences?.edit()?.clear()?.apply()
                        var Intent = Intent(this,PaySuccesActivity::class.java)
                        startActivity(Intent)
                        var dialog : AlertDialog = a.show()
                        dialog.dismiss()
                        finish()
                    }
                    Log.e("check",it.toString())
                }else{
                    Toast.makeText(this,"data : null",Toast.LENGTH_LONG).show()
                }

            })
            Log.e("check order",gson.toJson(getOrder))
            viewModel.ApiTest(gson.toJson(getOrder))


    }
    fun  payVnpay(){
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var getInfo = sharedPreferences.getString("infoUser","")
        var gson = Gson()

        var sharedPreference = getSharedPreferences("data_cart", Context.MODE_PRIVATE)!!
        var cart = sharedPreference.getString("cart", "")
        var item = object : TypeToken<ArrayList<cartModel>>() {}.type
        var getCart: ArrayList<cartModel> = gson.fromJson(cart, item)!!

        var totalMain : TextView = findViewById(R.id.totalMain)

        var getInfoUser : person = gson.fromJson(getInfo,person::class.java)

        var getOrder = orderModel(totalMain.text.toString(),getCart,getInfoUser.id_per,12,"vnpay","")

        var viewModel = ViewModelProvider(this).get(payViewModel::class.java)

        viewModel.getDataOrders().observe(this, Observer<String> {
            if (it != null ){
                var Intent = Intent(baseContext,webViewPayVnPayActivity::class.java)
                Intent.putExtra("web",it)
                startActivity(Intent)
                finish()

                Log.e("check",it.toString())
            }else{
                Toast.makeText(this,"data : null",Toast.LENGTH_LONG).show()
            }

        })
        Log.e("check order",gson.toJson(getOrder))
        viewModel.ApiTest(gson.toJson(getOrder))

    }
    fun payMomo(){

        var gson = Gson()
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var getInfo = sharedPreferences.getString("infoUser","")
        var sharedPreferencesCart = getSharedPreferences("data_cart", MODE_PRIVATE)
        var cart = sharedPreferencesCart.getString("cart","")
        var item = object  : TypeToken<ArrayList<cartModel>>(){}.type
        var getCart : ArrayList<cartModel> = gson.fromJson(cart,item)
        var getInfoUser : person = gson.fromJson(getInfo,person::class.java)


        var totalMain : TextView = findViewById(R.id.totalMain)

        var getOrder = orderModel(totalMain.text.toString(),getCart,getInfoUser.id_per,12,"momo","")

        var viewModel = ViewModelProvider(this).get(payViewModel::class.java)
        viewModel.getDataOrders().observe(this, Observer {
            if(it!=null){
                var Intent = Intent(baseContext,webViewPayMoMoActivity::class.java)
                Intent.putExtra("web",it)
                startActivity(Intent)
                finish()
                Log.e("check MoMo",it.toString())
            }else{
                Toast.makeText(this,"data null",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.ApiTest(gson.toJson(getOrder))
    }
}