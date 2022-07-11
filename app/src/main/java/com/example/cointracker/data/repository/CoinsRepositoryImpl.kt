package com.example.cointracker.data.repository

import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.repository.dataSource.CoinsRemoteDataSource
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CoinsRepositoryImpl(
    private val coinsRemoteDataSource: CoinsRemoteDataSource
): CoinsRepository {

    private fun responseToResource(response: Response<CoinList>): Resource<CoinList> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getCoinsFromAPI(): Resource<CoinList> {
        return responseToResource(coinsRemoteDataSource.getCoinsFromAPI())
    }

    override suspend fun saveCoinToDB(coin: Coin) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCoin(coin: Coin) {
        TODO("Not yet implemented")
    }

    override fun getSavedCoins(): Flow<List<Coin>> {
        TODO("Not yet implemented")
    }
}