package com.app.figure.UI.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.figure.Model.cartModel
import com.app.figure.Model.person
import com.app.figure.Model.userModel
import com.app.figure.R
import com.app.figure.UI.fragment.BlogFragment
import com.app.figure.UI.fragment.HomeFragment
import com.app.figure.UI.fragment.UserFragment
import com.app.figure.UI.fragment.ProductFragment
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import com.app.figure.viewmodel.loginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideSystemUI()


        var fr: FrameLayout = findViewById(R.id.cart_frame)
        fr.setOnClickListener {
            var intent = Intent(this,cartActivity::class.java)
            startActivity(intent)

        }

        var search : AppCompatImageView = findViewById(R.id.search_pr)

        search.setOnClickListener {
            var intent = Intent(this,SearcgActivity::class.java)
            startActivity(intent)

        }

        var navigation: ChipNavigationBar = findViewById(R.id.navigation_bottom)

        moveToFragment(HomeFragment())

        navigation.setItemSelected(R.id.home,true)

        navigation.setOnItemSelectedListener { item ->
            when (item) {
                R.id.home -> {
                    moveToFragment(HomeFragment())
                }
                R.id.product -> {
                    moveToFragment(ProductFragment())
                }
                R.id.blog -> {
                    moveToFragment(BlogFragment())
                }
                R.id.user -> {
                    moveToFragment(UserFragment())
                }
            }
            false
        }

       setUser()

    }


    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

    private fun moveToFragment(fag: Fragment) {
        var fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fag)
        fragmentTrans.commit()

    }

    override fun onResume() {
        super.onResume()
        var numCart : TextView = findViewById(R.id.countCart)
        numCart.text =  getNumberCart()
        getCurrentInfoUser()
    }

    private fun getCurrentInfoUser(){
        val preferences = this?.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val json: String = preferences?.getString("user", "")!!
        if(!json.isEmpty()){
            val gson = Gson()
            val obj: userModel = gson.fromJson(json, userModel::class.java)
            var viewModel = ViewModelProvider(this).get(loginViewModel::class.java)

            val token: String? = preferences?.getString("token", "")
            if(!token.isNullOrEmpty()){
                com.app.figure.retrofit.retroInstance.YOUR_TOKEN = token
            }

            viewModel.ApigetInfoUser(obj.id_ac.toString())
            viewModel.getPersons().observe(this, Observer<person> {
                if(it != null){
                    val json = gson.toJson(it)

                    preferences?.edit()?.putString("infoUser",json)?.commit()
                }else{
                    Log.e("check user 2 :", "null" + json)
                }
            })
        }
    }
    fun getNumberCart(): String {
        var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
        var cart = sharedPreferences.getString("cart","")
        var gson = Gson()
        if(!cart!!.isEmpty()) {

            var itemType = object : TypeToken<List<cartModel>>() {}.type

            var getCart = gson.fromJson<ArrayList<cartModel>>(cart, itemType)

            return getCart.size.toString()
        }
        return "0"
    }

    private fun setUser(){

        var name_user : TextView = findViewById(R.id.name_user)
        var welcome : TextView = findViewById(R.id.welcome)
        val preferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val json: String = preferences.getString("user", "")!!
        if(!json.isEmpty()){
            val gson = Gson()
            val obj: userModel = gson.fromJson(json, userModel::class.java)
            name_user.text = obj.username.toString()

        }else{
            name_user.text = "customer"

        }
    }


}