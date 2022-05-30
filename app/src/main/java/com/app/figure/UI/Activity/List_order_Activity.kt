package com.app.figure.UI.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.Model.getDataOrderModel
import com.app.figure.Model.person
import com.app.figure.R
import com.app.figure.UI.Adapter.User.selectOderAdapter
import com.app.figure.viewmodel.fragmentUserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class List_order_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_order)
        setTableData()
        backActivity()
        hideSystemUI()
    }

    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
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

    fun setTableData() {
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var getInfo = sharedPreferences.getString("infoUser", "")
        val token: String? = sharedPreferences?.getString("token", "")
        if (!token?.isEmpty()!!) {
            var recyclerView: RecyclerView = findViewById(R.id.table_recycler_view)

            var adapter: selectOderAdapter = selectOderAdapter(this)
            recyclerView.layoutManager = LinearLayoutManager(this)


            var viewModel = ViewModelProvider(this).get(fragmentUserViewModel::class.java)
            viewModel.getDataOrders().observe(this, Observer {
                if (it != null) {
                    adapter.setList(it)
                    adapter.notifyDataSetChanged()
                }else{
                    adapter.setList(null as ArrayList<getDataOrderModel>)
                    adapter.notifyDataSetChanged()
                }
            })


            var gson = Gson()
            var info: person = gson.fromJson(getInfo, person::class.java)

            com.app.figure.retrofit.retroInstance.YOUR_TOKEN = token

            viewModel.ApiCallDataOrder(info.id_ac.toString())
            recyclerView.adapter = adapter
        } else {
            MaterialAlertDialogBuilder(this)
                .setMessage("Vui lòng đăng nhập để tiếp tục")
                .setPositiveButton("canel", { dialog, which ->
                    dialog.dismiss()
                    finish()
                }).show()
        }
    }
}