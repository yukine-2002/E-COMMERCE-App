package com.app.figure.UI.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.app.figure.R

class PaySuccesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_succes)
        findViewById<Button>(R.id.back).setOnClickListener {
            var Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }
    }
}