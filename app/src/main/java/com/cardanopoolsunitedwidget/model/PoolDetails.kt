package com.cardanopoolsunitedwidget.model
import com.google.gson.annotations.SerializedName


data class PoolDetails(
    @SerializedName("active_size")
    val activeSize: Double,
    @SerializedName("active_stake")
    val activeStake: String,
    @SerializedName("blocks_minted")
    val blocksMinted: Int,
    @SerializedName("declared_pledge")
    val declaredPledge: String,
    @SerializedName("fixed_cost")
    val fixedCost: String,
    val hex: String,
    @SerializedName("live_delegators")
    val liveDelegators: Int,
    @SerializedName("live_pledge")
    val livePledge: String,
    @SerializedName("live_saturation")
    val liveSaturation: Double,
    @SerializedName("live_size")
    val liveSize: Double,
    @SerializedName("live_stake")
    val liveStake: String,
    @SerializedName("margin_cost")
    val marginCost: Double,
    val owners: List<String>,
    @SerializedName("pool_id")
    val poolId: String,
    val registration: List<String>,
    val retirement: List<Any>,
    @SerializedName("reward_account")
    val rewardAccount: String,
    @SerializedName("vrf_key")
    val vrfKey: String
)