package com.app.figure.UI.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.app.figure.R

class webViewPayMoMoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_pay_mo_mo)
        var url = intent.getStringExtra("web")

//        9704198526191432198

        var webview : WebView = findViewById(R.id.webview)
        webview.webViewClient = object  : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        if (url != null) {
            webview.loadUrl(url)
        }

        webview.settings.javaScriptEnabled =true
        webview.settings.allowFileAccess = true
        webview.settings.domStorageEnabled = true
        webview.settings.useWideViewPort = true
        webview.settings.setAppCacheEnabled(true)

        backActivity()

    }
    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}