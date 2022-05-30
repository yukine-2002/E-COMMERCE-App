package com.app.figure.UI.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.figure.Model.googleData
import com.app.figure.Model.responeLogin
import com.app.figure.R
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import com.app.figure.viewmodel.loginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN = 27
    lateinit var gso: GoogleSignInOptions
     lateinit var gsc: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username:EditText = findViewById(R.id.username)
        var password:EditText = findViewById(R.id.password)

        var btnLogin : Button = findViewById(R.id.btnLogin)

        var changSignup : TextView = findViewById(R.id.layoutSignup)

        changSignup.setOnClickListener {
            var intent =  Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            Login(username.text.toString(), password.text.toString())
        }




        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        signInButton.setOnClickListener(View.OnClickListener { signIn() })



    }
    fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                if (account != null) {
                    val personName: String = account.displayName.toString()
                    val personGivenName: String = account.givenName.toString()
                    val personFamilyName: String = account.familyName.toString()
                    val personEmail: String = account.email.toString()
                    val personId: String = account.id.toString()
                    val personPhoto: Uri? = account.photoUrl
                    Log.w("check","dataa : " + personName + " "+ personGivenName + " "+ personFamilyName + " "+ personEmail + " "+ personId + " "+ personId + " "+personPhoto)

                    var viewmodel = ViewModelProvider(this).get(loginViewModel::class.java)
                    var gson = Gson()
                    var data = gson.toJson(googleData(account.displayName.toString(),account.givenName.toString(),account.email.toString(),account.id.toString()))
                    viewmodel.ApiCheck(data)
                    viewmodel.GetLoginWithGoogle().observe(this, Observer {
                        if(it.status == 201){
                            var Intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(Intent)
                            finish()
                            val preferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

                            preferences.edit().putString("token",it.token.toString()).commit()

                            val json = gson.toJson(it.user)
                            preferences.edit().putString("user",json).commit()
                            Toast.makeText(baseContext,"thanh cong ", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(baseContext,"that bai ", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

            } catch (e: ApiException) {
                Log.e("fail", e.toString())
                Toast.makeText(applicationContext, "Something went wrong ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val preferences = this?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token: String? = preferences?.getString("token", "")
        if(!token?.isEmpty()!!){
            var Intent = Intent(baseContext, MainActivity::class.java)
            startActivity(Intent)
            finish()

        }

    }


    fun validateUsername(): Boolean {
        var username : EditText = findViewById(R.id.username)
        val noWhiteSpace: Regex = "\\A\\w{4,20}\\z".toRegex()

        if(username?.text.toString().isEmpty()){
            username.error = "Field cannot be empty"
            return false
        }
        else if(username?.text.toString().length >= 15){
            username.error="username too long"
            return false
        }
        else if(!username?.text.toString().matches(noWhiteSpace)) {
            username.error="White Spaces are not allowed"
            return false
        }
        else
        {
            username.error = ""
            return true
        }
    }

    fun validatePassword(): Boolean {
        var password : EditText = findViewById(R.id.password)
        val passwordVal : Regex = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$".toRegex()


        if(password?.text.toString().isEmpty()){
            password.error = "Field cannot be empty"
            return false
        }
        else if(!password?.text.toString().matches(passwordVal)){
            password.error = "Password is too weak"
            return false
        }
        else
        {
            password.error = ""
            return true
        }
    }

    fun Login(username : String , password : String){

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setTitle("Đăng nhập")
        progressDialog.setMessage("Vui lòng đợi một chút....")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        if(validateUsername() || validatePassword()){

            var retroInstance = retroInstance.getRetroInstance()
            var retroservice = retroInstance.create(RetroServiceInterface::class.java)
            var call = retroservice.Login(username,password)
            call.enqueue(object : Callback<responeLogin> {
                override fun onResponse(call: Call<responeLogin>, response: Response<responeLogin>) {
                    if(response.isSuccessful){
                        if(response.code() == 201){
                            progressDialog.dismiss()

                            com.app.figure.retrofit.retroInstance.YOUR_TOKEN = response.body()?.token
                            var Intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(Intent)
                            finish()

                            val preferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

                            preferences.edit().putString("token",response.body()?.token.toString() ).commit()

                            val gson = Gson()
                            val json = gson.toJson(response.body()?.user)
                            preferences.edit().putString("user",json).commit()

                        }
                    }else{
                        progressDialog.dismiss()
                        Toast.makeText(baseContext,"that bai ", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<responeLogin>, t: Throwable) {
                    print(t)
                    progressDialog.dismiss()

                    Toast.makeText(baseContext,"that bai ", Toast.LENGTH_SHORT).show()

                }
            })
        }else{
                progressDialog.dismiss()
                Toast.makeText(baseContext,"đăng nhập thất bại", Toast.LENGTH_SHORT).show()
        }

    }




}