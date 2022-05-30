package com.app.figure.retrofit

import com.app.figure.config.baseURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retroInstance {

    companion object {
        val Base_URL = baseURL().BASE_URL+"/api/"

        var YOUR_TOKEN : Any? = null

        fun getRetroInstance(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl(Base_URL)
                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${YOUR_TOKEN}").build()
                    chain.proceed(request)
                }.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit;

        }
    }

}