package com.example.cointracker.data.repository.dataSource

import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import retrofit2.Response

interface CoinsRemoteDataSource {

    suspend fun getCoinsFromAPI() : Response<CoinList>

}