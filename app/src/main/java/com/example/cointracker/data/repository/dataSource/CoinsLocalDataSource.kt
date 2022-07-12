package com.example.cointracker.data.repository.dataSource

import com.example.cointracker.data.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinsLocalDataSource {

    suspend fun saveCoinToDB(coin: Coin)

    fun getSavedCoinsFromDB(): Flow<List<Coin>>

    suspend fun deleteCoin(coin: Coin)

}