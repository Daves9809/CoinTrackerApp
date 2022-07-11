package com.example.cointracker.data.model


import com.google.gson.annotations.SerializedName

data class CoinList(
    @SerializedName("data")
    val `data`: List<Coin?>?,
    @SerializedName("status")
    val status: Status?
)