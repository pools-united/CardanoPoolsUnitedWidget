package com.cardanopoolsunitedwidget.service

import com.cardanopoolsunitedwidget.data.network.RetrofitResponse
import com.cardanopoolsunitedwidget.model.Epoch
import com.cardanopoolsunitedwidget.model.PoolDetails

object PoolDataService {
    fun getCurrentEpoch(api: RetrofitResponse): Epoch {
        val apiUrl = "api/v0/epochs/latest";
        val response = api.retrofitApiService().getLatestEpoch(apiUrl).execute()

        if(response.isSuccessful) {
            return response.body()!!;
        } else {
            throw Exception("failed get pool blocks");
        }
    }
    fun getPoolBlocks(epoch: Int, poolID: String, api: RetrofitResponse): String {
        val apiUrl = "api/v0/epochs/$epoch/blocks/$poolID";
        val response = api.retrofitApiService().getSpecificPoolEpochDetails(apiUrl).execute()

        if(response.isSuccessful) {
            return  response.body()?.size.toString();
        } else {
            throw Exception("failed get pool blocks");
        }
    }

    fun getSpecificPoolData(poolID: String, api: RetrofitResponse): PoolDetails? {
        val poolApiURL = "api/v0/pools/$poolID";
        val response = api.retrofitApiService().getSpecificPoolDetails(poolApiURL).execute();
        if(response.isSuccessful) {
            return response.body()!!;
        } else {
            throw Exception("failed get pool data");
        }
    }

    fun withSuffix(count: Long): String? {
        if (count < 1000) return "" + count
        val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kkkMGTPE"[exp - 1]
        )
    }
}