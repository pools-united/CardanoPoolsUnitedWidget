package com.cardanopoolsunitedwidget.data.network

import com.cardanopoolsunitedwidget.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitResponse {
    fun retrofitApiService():RestAPIService {
        val requestInterceptor = Interceptor { chain ->

            val builder = chain.request().newBuilder()
            builder.header("project_id", "NCIvdtG1BSS6Gaj9wp6xmevYVAV8SLYk")
            return@Interceptor chain.proceed(builder.build())
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestAPIService::class.java)
    }



}