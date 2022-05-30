package com.app.figure.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.person
import com.app.figure.Model.productModels
import com.app.figure.R
import com.app.figure.UI.Adapter.productAdapter.productAdapter
import com.app.figure.retrofit.retroInstance
import com.app.figure.viewmodel.fragmentProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class FavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
        setDataAdapter()
        backActivity()
    }

    private fun setDataAdapter(){
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var Info: String = sharedPreferences.getString("infoUser", "")!!
        var token: String = sharedPreferences.getString("token", "")!!
        if (!token.isNullOrEmpty()) {
            var gson = Gson()
            var getInfo: person = gson.fromJson(Info, person::class.java)
            var recyclerView : RecyclerView = findViewById(R.id.favProduct)
            var adapter : productAdapter =  productAdapter(this)
            recyclerView.layoutManager = GridLayoutManager(this,2)
            var viewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
            viewModel.getDataFavProducts().observe(this, Observer<List<productModels>> {
                if(it!=null){
                    Log.e("check data",it.toString())
                    adapter.setTopFigureAdapter(it)
                    adapter.notifyDataSetChanged()
                }
            })
            recyclerView.adapter = adapter
            viewModel.ApiFavProduct(getInfo.id_per.toString())
            retroInstance.YOUR_TOKEN = token

        } else {
            MaterialAlertDialogBuilder(this)
                .setMessage("Vui lòng đăng nhập để tiếp tục")
                .setPositiveButton("canel", { dialog, which ->
                    dialog.dismiss()
                }).show()
        }
    }

    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

}