package com.example.cointracker.data.repository.dataSourceImpl

import com.example.cointracker.data.api.CoinAPIService
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.repository.dataSource.CoinsRemoteDataSource
import com.example.cointracker.data.util.Resource
import retrofit2.Response

class CoinsRemoteDataSourceImpl(
    private val coinAPIService: CoinAPIService
): CoinsRemoteDataSource {

    override suspend fun getCoinsFromAPI(): Response<CoinList> {
        return coinAPIService.getCoinsFromAPI()
    }

}