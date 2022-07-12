package com.example.cointracker.data.model


import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("market_cap")
    val marketCap: Double?,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double?,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double?,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double?,
    @SerializedName("price")
    val price: Double?,
)