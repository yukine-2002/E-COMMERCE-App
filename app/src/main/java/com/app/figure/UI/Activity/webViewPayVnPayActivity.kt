package com.app.figure.UI.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.figure.Model.cartModel
import com.app.figure.Model.dataConfimVnPay
import com.app.figure.Model.person
import com.app.figure.Model.vnpayReturn
import com.app.figure.R
import com.app.figure.viewmodel.payViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class webViewPayVnPayActivity : AppCompatActivity() {

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_pay)

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

        webview.setWebViewClient(object : WebViewClient() {

            override fun onPageFinished(view: WebView, address: String) {
                webview.evaluateJavascript(
                    "(function() { document.getElementsByTagName('pre')[0].style.display= 'none' \n" +
                            "             return ( document.getElementsByTagName('pre')[0].innerHTML)\n" +
                            "            })();",
                    ValueCallback<String?> { html ->
                        if(!html.equals("null")){
                           try {
                               var json = html.replace("\\\"","'");
                               var obj :JSONObject = JSONObject(json.substring(1,json.length-1))
                               var gson = Gson()

                               var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
                               var cart = sharedPreferences.getString("cart","")
                               var itemType = object  : TypeToken<ArrayList<cartModel>>(){}.type
                               var getCart : ArrayList<cartModel> = gson.fromJson<ArrayList<cartModel>>(cart,itemType)!!

                               var sharedPreferenceUser = getSharedPreferences("myPrefs", MODE_PRIVATE)
                               var getInfo = sharedPreferenceUser.getString("infoUser","")
                               var infoUser : person = gson.fromJson(getInfo, person::class.java)

                               var VnPayReturn : vnpayReturn = gson.fromJson(obj.toString(),vnpayReturn::class.java)

                                if(VnPayReturn.vnp_ResponseCode.equals("00")){
                                    var  a =  MaterialAlertDialogBuilder(this@webViewPayVnPayActivity)
                                        a.setMessage("Đang thực hiện thanh toán vui lòng đợi")
                                    var dataConfim = dataConfimVnPay(infoUser.id_ac,1,getCart,VnPayReturn.vnp_Amount,VnPayReturn.vnp_TxnRef)
                                    var confimVnPay : String = gson.toJson(dataConfim)
                                    Log.e("confim", confimVnPay)
                                    a.show()

                                    getDataVnPay(confimVnPay,a)
                                    Log.d("HTML", VnPayReturn.vnp_ResponseCode)

                                }else{
                                    var Intent = Intent(this@webViewPayVnPayActivity, ErrorPayActivity::class.java)
                                    startActivity(Intent)
                                }
                           }catch (e : JSONException){
                               Log.e("MYAPP", "unexpected JSON exception", e);
                           }
                        }

                    })
            }
        })
        backActivity()
    }

    fun getDataVnPay(data : String, ma : MaterialAlertDialogBuilder){
        var viewModel = ViewModelProvider(this).get(payViewModel::class.java)
        viewModel.dataReturn(data)
        viewModel.getVnPayReturns().observe(this, Observer {
            if(it!=null){
                Log.e("it : " , it.toString())
                if(it.toInt() == 201){
                    var sharedPreferences = getSharedPreferences("data_cart", MODE_PRIVATE)
                    sharedPreferences?.edit()?.clear()?.apply()
                    val dialog: AlertDialog = ma.show()
                    var Intent = Intent(this,PaySuccesActivity::class.java)
                    startActivity(Intent)
                    dialog.dismiss()
                    finish()
                    Log.e("check Data Return", it.toString())
                }
            }
        })

    }
    private fun backActivity() {
        var back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}