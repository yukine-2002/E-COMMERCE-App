package com.app.figure.UI.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.cartModel
import com.app.figure.Model.person
import com.app.figure.Model.productModels
import com.app.figure.R
import com.app.figure.UI.Adapter.anotherAdapter.cartAdapter
import com.app.figure.config.OnClickGetItemCart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class cartActivity : AppCompatActivity(),OnClickGetItemCart {

    private var i : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        var getMoney : TextView = findViewById(R.id.money)
        setTotal(getMoney)
        var remove:TextView = findViewById(R.id.remove)
        hideSystemUI()
        getDataCart(remove,getMoney)
        removeCart()
        backActivity()
    }

    override fun onResume() {
        super.onResume()
        buttonAction()
    }

    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }


    fun getDataCart(textView: TextView? = null,getMoney: TextView? = null){
        var actionButton : Button = findViewById(R.id.actionCart)
        var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
        var cart = sharedPreferences.getString("cart","")
        if(cart?.isEmpty() != true){
            actionButton.text="Thanh toán"
            val itemType = object : TypeToken<List<cartModel>>() {}.type
            var gson  = Gson()
            var getCart : ArrayList<cartModel> = gson.fromJson<ArrayList<cartModel>>(cart,itemType)!!
            var cartAdapter : cartAdapter = cartAdapter(this,textView!!,i,getMoney!!)
            cartAdapter.getOnClick(this)
            var recyclerView : RecyclerView = findViewById(R.id.rcCart)
            recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            cartAdapter.getList(getCart)
            recyclerView.adapter = cartAdapter
        }else{
            var recyclerView : RecyclerView = findViewById(R.id.rcCart)
            recyclerView.adapter = null
            actionButton.text="Tiếp tục mua hàng"
        }

    }

    fun setTotal(getMoney : TextView){
        var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
        var cart = sharedPreferences.getString("cart","")
        var gson = Gson()
        if(!cart!!.isEmpty()) {
            var item = object : TypeToken<ArrayList<cartModel>>() {}.type
            var getCart: ArrayList<cartModel> = gson.fromJson(cart, item)
            var money: Int = 0
            for (items in getCart) {
                var b = items.quantity!!.toInt()
                var c = (b * items.price!!.toInt())
                money += c
            }
            getMoney.text = "${money}"

        }else{
            getMoney.text = "0"
        }
    }


    fun removeCart(){
        var remv:TextView = findViewById(R.id.remove)
            remv.setOnClickListener {
                if (remv.isVisible){
                var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
                sharedPreferences?.edit()?.clear()?.apply()
                remv.visibility = View.GONE
                getDataCart()
            }
        }
    }

    fun buttonAction(){
        var actionButton : Button = findViewById(R.id.actionCart)

        actionButton.setOnClickListener {
            if(actionButton.text == "Thanh toán"){
                var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
                var token = sharedPreferences.getString("token","")
                if(!token!!.isEmpty()){
                    var getInfo = sharedPreferences.getString("infoUser","")
                    var gson = Gson()
                    var getInfoUser : person = gson.fromJson(getInfo, person::class.java)
                    if(!getInfoUser.fullname.isNullOrEmpty() && !getInfoUser.email.isNullOrEmpty() && !getInfoUser.cmnd.isNullOrEmpty() && !getInfoUser.adress.isNullOrEmpty()){
                        var Intent = Intent(this,PayActivity::class.java)
                        var money : TextView = findViewById(R.id.money)
                        Intent.putExtra("total",money.text.toString())
                        startActivity(Intent)
                        finish()
                        Toast.makeText(this,"Thanh toán",Toast.LENGTH_SHORT).show()
                    }else{
                        MaterialAlertDialogBuilder(this)
                            .setMessage("Vui long nhập đầy đủ thông tin trước khi thanh toán")
                            .setPositiveButton("ok", { dialog,which ->
                                dialog.dismiss()
                            }).show()
                    }
                }else{
                    MaterialAlertDialogBuilder(this)
                        .setMessage("Vui Lòng đăng nhập đẻ tiếp tục")
                        .setPositiveButton("ok" ,{ dialog,which ->
                            dialog.dismiss()
                        }).show()
                }
            }else if(actionButton.text == "Tiếp tục mua hàng"){
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Tiếp tục mua hàng",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

    override fun getItem(i: Int) {
        var item : TextView = findViewById(R.id.selectItem)
        item.text = "Select item : $i"
    }

    override fun getMoney(i: Int) {
        var item : TextView = findViewById(R.id.money)
        item.text = "${i}"
    }


    fun formatMoney(money : Int) : String {
        val localeVN = Locale("vi", "VN")
        val numberFormat = NumberFormat.getCurrencyInstance(localeVN)
        numberFormat.setMaximumFractionDigits(0);
        val convert = numberFormat.format(money)

        return convert

    }
}