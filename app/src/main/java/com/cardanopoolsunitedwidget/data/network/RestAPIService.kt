package com.cardanopoolsunitedwidget.data.network

import com.cardanopoolsunitedwidget.model.PoolDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPIService {

    @GET("/pools/19cb138eab81d3559e70094df2b6cb1742bf275e920300d5c3972253/summary.json")
    fun getSpecificPoolDetails(
    ): Call<PoolDetails>
}