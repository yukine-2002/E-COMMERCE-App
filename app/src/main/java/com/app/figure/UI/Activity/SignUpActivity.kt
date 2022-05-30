package com.app.figure.UI.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.figure.R
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var signup: Button = findViewById(R.id.signup)
        var username: EditText = findViewById(R.id.usernamesu)
        var password: EditText = findViewById(R.id.confim_password)
        signup.setOnClickListener {
            Register(username.text.toString(), password.text.toString())
        }
        var changSignup: TextView = findViewById(R.id.layoutSignin)

        changSignup.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun validateUsername(): Boolean {
        var username: EditText = findViewById(R.id.usernamesu)
        val noWhiteSpace: Regex = "\\A\\w{4,20}\\z".toRegex()

        if (username?.text.toString().isEmpty()) {
            username.error = "Field cannot be empty"
            return false
        } else if (username?.text.toString().length >= 15) {
            username.error = "username too long"
            return false
        } else if (!username?.text.toString().matches(noWhiteSpace)) {
            username.error = "White Spaces are not allowed"
            return false
        } else {
            username.error = ""
            return true
        }
    }

    fun validatePassword(): Boolean {
        var password: EditText = findViewById(R.id.passwordsu)
        val passwordVal: Regex = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$".toRegex()

        if (password?.text.toString().isEmpty()) {
            password.error = "Field cannot be empty"
            return false
        } else if (!password?.text.toString().matches(passwordVal)) {
            password.error = "Password is too weak"
            return false
        } else {
            password.error = ""
            return true
        }
    }

    fun validateConfimPassword(): Boolean {
        var password: EditText = findViewById(R.id.confim_password)
        var password_check: EditText = findViewById(R.id.passwordsu)
        val passwordVal: Regex = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$".toRegex()

        if (password?.text.toString().isEmpty()) {
            password.error = "Field cannot be empty"
            return false
        } else if (!password?.text.toString().matches(passwordVal)) {
            password.error = "Password is too weak"
            return false
        } else if (!password?.text.toString().equals(password_check?.text.toString())) {
            password.error = "Password khong trung nhau"
            return false
        } else {
            password.error = ""
            return true
        }
    }


    fun Register(username: String, password: String) {

        val progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog.setTitle("Đăng kí")
        progressDialog.setMessage("Vui lòng đợi một chút...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        if (validateUsername() && validatePassword() && validateConfimPassword()) {
            var retroInstance = retroInstance.getRetroInstance()
            var retroservice = retroInstance.create(RetroServiceInterface::class.java)
            var call = retroservice.register(username, password)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            progressDialog.dismiss()
                            var Intent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(Intent)
                            Toast.makeText(baseContext, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(baseContext, "that bai ", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    print(t)
                    progressDialog.dismiss()
                    Toast.makeText(baseContext, "that bai ", Toast.LENGTH_SHORT).show()

                }
            })
        } else {
            progressDialog.dismiss()
            Toast.makeText(baseContext, "đăng ky thất bại", Toast.LENGTH_SHORT).show()
        }

    }

}

