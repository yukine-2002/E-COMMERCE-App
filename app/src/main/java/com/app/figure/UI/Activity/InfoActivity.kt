package com.app.figure.UI.Activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.figure.Model.person
import com.app.figure.R
import com.app.figure.config.RealPathUtil
import com.app.figure.config.baseURL
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import com.app.figure.viewmodel.fragmentUserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class InfoActivity : AppCompatActivity() {

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    var files : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        backActivity()
        hideSystemUI()
        setData()
        pickImageFromGallery()

        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val getpermission = Intent()
                getpermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(getpermission)
            }
        }

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

    private fun setData() {
        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val token: String? = sharedPreferences?.getString("token", "")
        var gson = Gson()
        if (!token?.isEmpty()!!) {
            var name: TextView = findViewById(R.id.name_u)
            var user: TextView = findViewById(R.id.user)
            var cmnd: TextView = findViewById(R.id.cmnd)
            var date: TextView = findViewById(R.id.date)
            var sdt: TextView = findViewById(R.id.sdt)
            var email: TextView = findViewById(R.id.email)
            var address: TextView = findViewById(R.id.address)
            var userInfo = sharedPreferences.getString("infoUser", "")
            var img: ImageView = findViewById(R.id.img)

            if (!userInfo!!.isEmpty()) {
                var getInfo: person = gson.fromJson(userInfo, person::class.java)
                name.text = getInfo.fullname
                user.text = getInfo.username
                cmnd.text = getInfo.cmnd
                date.text = getInfo.dates
                sdt.text = "0" + getInfo.sdt.toString()!!
                email.text = getInfo.email
                address.text = getInfo.adress
                val imgs = baseURL().BASE_URL+"/layout/Img/"+getInfo?.img
                Picasso.get().load(imgs).placeholder(R.drawable.ic_bg_2_1).into(img)
            }
            updateData()
        } else {
            MaterialAlertDialogBuilder(this)
                .setMessage("Vui lòng đăng nhập để tiếp tục")
                .setPositiveButton("canel", { dialog, which ->
                    dialog.dismiss()
                    finish()
                }).show()
        }
    }

    private fun updateData(){
        var name: TextView = findViewById(R.id.name_u)
        var cmnd: TextView = findViewById(R.id.cmnd)
        var date: TextView = findViewById(R.id.date)
        var sdt: TextView = findViewById(R.id.sdt)
        var email: TextView = findViewById(R.id.email)
        var address: TextView = findViewById(R.id.address)
        var img: ImageView = findViewById(R.id.img)

        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var userInfo = sharedPreferences.getString("infoUser", "")
        var gson = Gson()
        var getInfo: person = gson.fromJson(userInfo, person::class.java)

        val imgs = baseURL().BASE_URL+"/layout/Img/"+getInfo?.img
        Picasso.get().load(imgs).placeholder(R.drawable.ic_bg_2_1).into(img)

        name.setOnClickListener {
            var layoutInflater = LayoutInflater.from(this)
            var v = layoutInflater.inflate(R.layout.add_item,null)
            var nameUpdate : EditText = v.findViewById(R.id.name)

            nameUpdate.text = Editable.Factory().newEditable(name.text)

            MaterialAlertDialogBuilder(this)
                .setView(v)
                .setPositiveButton("Lưu") {dialog, which ->
                var title:TextView = v.findViewById(R.id.title)

                title.text = "Sửa tên"
                updateUser("updateName",nameUpdate.text.toString(),getInfo.id_per.toString())
                getInfo.fullname = nameUpdate.text.toString()
                sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                setData()
                    dialog.dismiss()
            }
                .setNegativeButton("canel") {dialog,which ->
                    dialog.dismiss()
                }.show()
        }

        cmnd.setOnClickListener {
            var layoutInflater = LayoutInflater.from(this)
            var v = layoutInflater.inflate(R.layout.add_item,null)
            var nameUpdate : EditText = v.findViewById(R.id.name)

            nameUpdate.text = Editable.Factory().newEditable(cmnd.text)

            MaterialAlertDialogBuilder(this)
                .setView(v)
                .setPositiveButton("Lưu") {dialog, which ->
                    var title:TextView = v.findViewById(R.id.title)

                    title.text = "Sửa CMND"
                    updateUser("updateCmnd",nameUpdate.text.toString(),getInfo.id_per.toString())
                    getInfo.cmnd = nameUpdate.text.toString()
                    sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                    setData()
                    dialog.dismiss()
                }
                .setNegativeButton("canel") {dialog,which ->
                    dialog.dismiss()
                }.show()
        }

        date.setOnClickListener {
            var cal = Calendar.getInstance()
            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                       dayOfMonth: Int) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "yyyy/MM/dd"
                    val sdf = SimpleDateFormat(myFormat, Locale.US)

                    updateUser("updatedates", sdf.format(cal.getTime()),getInfo.id_per.toString())
                    getInfo.dates =  sdf.format(cal.getTime())
                    sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                    setData()

                }
            }
            DatePickerDialog(this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

        }

        sdt.setOnClickListener {
            var layoutInflater = LayoutInflater.from(this)
            var v = layoutInflater.inflate(R.layout.add_item,null)
            var nameUpdate : EditText = v.findViewById(R.id.name)

            nameUpdate.text = Editable.Factory().newEditable(sdt.text)

            MaterialAlertDialogBuilder(this)
                .setView(v)
                .setPositiveButton("Lưu") {dialog, which ->
                    var title:TextView = v.findViewById(R.id.title)

                    title.text = "Sửa SDT"
                    updateUser("updateSdt",nameUpdate.text.toString(),getInfo.id_per.toString())
                    getInfo.sdt = nameUpdate.text.toString().toInt()
                    sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                    setData()
                    dialog.dismiss()
                }
                .setNegativeButton("canel") {dialog,which ->
                    dialog.dismiss()
                }.show()
        }

        email.setOnClickListener {
            var layoutInflater = LayoutInflater.from(this)
            var v = layoutInflater.inflate(R.layout.add_item,null)
            var nameUpdate : EditText = v.findViewById(R.id.name)

            nameUpdate.text = Editable.Factory().newEditable(email.text)

            MaterialAlertDialogBuilder(this)
                .setView(v)
                .setPositiveButton("Lưu") {dialog, which ->
                    var title:TextView = v.findViewById(R.id.title)

                    title.text = "Sửa Ngày sinh"
                    updateUser("updateEmail",nameUpdate.text.toString(),getInfo.id_per.toString())
                    getInfo.email = nameUpdate.text.toString()
                    sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                    setData()
                    dialog.dismiss()
                }
                .setNegativeButton("canel") {dialog,which ->
                    dialog.dismiss()
                }.show()
        }


        address.setOnClickListener {
            var layoutInflater = LayoutInflater.from(this)
            var v = layoutInflater.inflate(R.layout.add_item,null)
            var nameUpdate : EditText = v.findViewById(R.id.name)

            nameUpdate.text = Editable.Factory().newEditable(address.text)

            MaterialAlertDialogBuilder(this)
                .setView(v)
                .setPositiveButton("Lưu") {dialog, which ->
                    var title:TextView = v.findViewById(R.id.title)
                    title.text = "Sửa Ngày sinh"
                    updateUser("updateAdress",nameUpdate.text.toString(),getInfo.id_per.toString())
                    getInfo.adress = nameUpdate.text.toString()
                    sharedPreferences.edit().putString("infoUser",gson.toJson(getInfo)).apply()
                    setData()
                    dialog.dismiss()
                }
                .setNegativeButton("canel") {dialog,which ->
                    dialog.dismiss()
                }.show()
        }
    }
    fun updateUser(action : String , value:String,id:String){
        var viewModel = ViewModelProvider(this).get(fragmentUserViewModel::class.java)
        val preferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token: String? = preferences?.getString("token", "")

        com.app.figure.retrofit.retroInstance.YOUR_TOKEN =  token
        viewModel.getStatusupdateUser().observe(this, Observer {
            if(it != null){
                Toast.makeText(this,""+it,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"that bai",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.ApiUpdateUser(action,value,id)
    }

    private fun pickImageFromGallery() {

        var button : Button = findViewById(R.id.changeImg)
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            var img : ImageView = findViewById(R.id.img)
            if(data?.data != null){
                img.setImageURI(data?.data)
                ApiUploadImg(RealPathUtil().getRealPath(this,data?.data!!)!!)
            }

        }
    }

    private fun ApiUploadImg(data : String){

        var sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        var userInfo = sharedPreferences.getString("infoUser", "")
        var gson = Gson()
        var getInfo: person = gson.fromJson(userInfo, person::class.java)
        val file = File(data)
        val requestBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)

        val parts: MultipartBody.Part = MultipartBody.Part.createFormData("img", file.name, requestBody)

        var retroInstance = retroInstance.getRetroInstance()

        var retroService = retroInstance.create(RetroServiceInterface::class.java)


        val token: String? = sharedPreferences?.getString("token", "none")

        com.app.figure.retrofit.retroInstance.YOUR_TOKEN =  token

        var call = retroService.UpdateImage(getInfo.id_ac,parts)

        call.enqueue(object  : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var gson = Gson()
               Log.e("check",gson.toJson(response.body()).toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("fail",t.toString())
            }

        })

    }

}