package com.cardanopoolsunitedwidget.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("active_blocks")
    val activeBlocks: String,
    @SerializedName("active_stake")
    val activeStake: String,
    @SerializedName("blocks_epoch")
    val blocksEpoch: String,
    @SerializedName("blocks_est_lifetime")
    val blocksEstLifetime: String,
    @SerializedName("blocks_estimated")
    val blocksEstimated: Double,
    @SerializedName("blocks_lifetime")
    val blocksLifetime: String,
    @SerializedName("db_description")
    val dbDescription: String,
    @SerializedName("db_name")
    val dbName: String,
    @SerializedName("db_ticker")
    val dbTicker: String,
    @SerializedName("db_url")
    val dbUrl: String,
    val delegators: String,
    val direct: Boolean,
    @SerializedName("group_basic")
    val groupBasic: String,
    val handles: Handles,
    @SerializedName("hash_id")
    val hashId: String,
    @SerializedName("hist_bpe")
    val histBpe: String,
    @SerializedName("hist_roa")
    val histRoa: String,
    val id: String,
    val metric: Double,
    val owners: String,
    val pledge: String,
    val pledged: String,
    @SerializedName("pool_id")
    val poolId: String,
    val rank: Int,
    @SerializedName("rewards_epoch")
    val rewardsEpoch: String,
    val roa: String,
    val saturated: Double,
    @SerializedName("stake_x_deleg")
    val stakeXDeleg: Double,
    @SerializedName("stamp_strike")
    val stampStrike: String,
    @SerializedName("tax_fix")
    val taxFix: String,
    @SerializedName("tax_fix_old")
    val taxFixOld: Any,
    @SerializedName("tax_ratio")
    val taxRatio: String,
    @SerializedName("tax_ratio_old")
    val taxRatioOld: Any,
    @SerializedName("tax_real")
    val taxReal: Double,
    @SerializedName("ticker_orig")
    val tickerOrig: String,
    @SerializedName("total_stake")
    val totalStake: String
)