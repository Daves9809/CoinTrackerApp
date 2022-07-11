package com.example.cointracker.data.api

import com.example.cointracker.BuildConfig
import com.example.cointracker.data.model.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPIService {

    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCoinsFromAPI(
        @Query("CMC_PRO_API_KEY")
        apiKey: String = BuildConfig.API_KEY
    ): Response<CoinList>


}