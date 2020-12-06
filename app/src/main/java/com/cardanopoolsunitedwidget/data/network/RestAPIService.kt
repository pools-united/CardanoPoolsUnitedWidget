package com.cardanopoolsunitedwidget.data.network

import com.cardanopoolsunitedwidget.model.PoolDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface RestAPIService {

    @GET
    fun getSpecificPoolDetails(@Url url: String?): Call<PoolDetails?>
}