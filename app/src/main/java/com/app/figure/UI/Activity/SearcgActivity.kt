package com.app.figure.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.figure.R
import com.app.figure.UI.Adapter.anotherAdapter.searchAdapter
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.viewmodel.fragmentProductViewModel

class SearcgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searcg)
        hideSystemUI()
        search()
        backActivity()
    }
    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
    fun search(){
        var search : EditText = findViewById(R.id.search)

        search.addTextChangedListener( object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null){
                    getDataSearch(p0.toString())
                }else{
                    getDataSearch("")
                }
            }

        })
    }

    fun getDataSearch(name : String){
        var recycleView : RecyclerView = findViewById(R.id.resultSearch)
        recycleView.setHasFixedSize(true)
        var adapter = searchAdapter(this)
        var ViewModel = ViewModelProvider(this).get(fragmentProductViewModel::class.java)
        ViewModel.getDataSearchProduct().observe(this, Observer {
            if(!name.equals("")){
                if(it!=null){
                    adapter.getList(it)
                    adapter.notifyDataSetChanged()
                    Log.e("name",it.toString())
                }
            }
        })
        ViewModel.ApiSearchProduct(name)
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
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
}