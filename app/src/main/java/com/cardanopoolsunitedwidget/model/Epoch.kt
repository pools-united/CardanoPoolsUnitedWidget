package com.cardanopoolsunitedwidget.model
import com.google.gson.annotations.SerializedName


data class Epoch(
    @SerializedName("active_stake")
    val activeStake: String,
    @SerializedName("block_count")
    val blockCount: Int,
    @SerializedName("end_time")
    val endTime: Int,
    val epoch: Int,
    val fees: String,
    @SerializedName("first_block_time")
    val firstBlockTime: Int,
    @SerializedName("last_block_time")
    val lastBlockTime: Int,
    val output: String,
    @SerializedName("start_time")
    val startTime: Int,
    @SerializedName("tx_count")
    val txCount: Int
)