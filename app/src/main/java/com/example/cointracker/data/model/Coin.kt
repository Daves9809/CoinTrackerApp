package com.example.cointracker.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "coin_table")
data class Coin(
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @PrimaryKey
    @SerializedName("cmc_rank")
    val cmcRank: Int?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("max_supply")
    val maxSupply: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("quote")
    val quote: Quote?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("tags")
    val tags: List<String?>?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
) : Serializable