package com.app.figure.UI.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.figure.Model.person
import com.app.figure.R
import com.app.figure.UI.Activity.*
import com.app.figure.retrofit.RetroServiceInterface
import com.app.figure.retrofit.retroInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         var view =  inflater.inflate(R.layout.fragment_user, container, false)
        setMenuAction(view)
        setComponentView(view)
        return view
    }


    fun setMenuAction(view: View){
        var div_user_4 : LinearLayout = view.findViewById(R.id.div_user_4)
        var div_user_6 : LinearLayout = view.findViewById(R.id.div_user_6)
        var div_user_8  : LinearLayout = view.findViewById(R.id.div_user_8)
        div_user_4.setOnClickListener {
             var Intent = Intent(context,InfoActivity::class.java)
            startActivity(Intent)
        }
        div_user_6.setOnClickListener {
            var Intent = Intent(context,List_order_Activity::class.java)
            startActivity(Intent)
        }
        div_user_8.setOnClickListener {
            var Intent = Intent(context,FavActivity::class.java)
            startActivity(Intent)
        }


    }

    override fun onStart() {
        super.onStart()
        val preferences = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token: String? = preferences?.getString("token", "")

        if(!token?.isEmpty()!!){
            var textActionLogin : TextView = view?.findViewById(R.id.textActionLogin)!!
            textActionLogin.text = "LogOut"
            LogOut()

        }else{
            var textActionLogin : TextView = view?.findViewById(R.id.textActionLogin)!!
            textActionLogin.text = "Login"
            var LogOut : LinearLayout = view?.findViewById(R.id.LogOut)!!

            LogOut.setOnClickListener {
                var Intent = Intent(context, LoginActivity::class.java)
                startActivity(Intent)
            }

        }
    }

    fun setComponentView(view: View){
        var money : TextView = view.findViewById(R.id.money)
        var countBuy : TextView = view.findViewById(R.id.countBuy)

        var preferences = context?.getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        var json: String = preferences?.getString("infoUser","")!!
        if(!json.isEmpty()){
            var gson  = Gson()
            var obj : person = gson.fromJson(json, person::class.java)

            money.text = obj.price.toString()
            countBuy.text = obj.quantity.toString()
        }else{
            money.text = ""
            countBuy.text = ""
        }

    }

    fun LogOutGoolge(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut()
    }

    fun LogOut(){

        var LogOut : LinearLayout = view?.findViewById(R.id.LogOut)!!

        LogOut.setOnClickListener {

            var retroInstance = retroInstance.getRetroInstance()

            var retroService = retroInstance.create(RetroServiceInterface::class.java)

            val preferences = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token: String? = preferences?.getString("token", "none")

            com.app.figure.retrofit.retroInstance.YOUR_TOKEN =  token

            var call = retroService.LogOut()


            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.code() == 200){

                        var Intent = Intent(context, MainActivity::class.java)
                        startActivity(Intent)

                        val sharedPref = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                        sharedPref?.edit()?.clear()?.apply()

                        LogOutGoolge()

                        Toast.makeText(context,"thanh cong",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"that bai" + token,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    print(t)
                    Toast.makeText(context,"that bai",Toast.LENGTH_SHORT).show()
                }

            })


        }


    }

}