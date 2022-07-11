package com.example.cointracker.domain.repository

import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    suspend fun getCoinsFromAPI(): Resource<CoinList>

    suspend fun saveCoinToDB(coin: Coin)

    suspend fun deleteCoin(coin: Coin)

    fun getSavedCoins() : Flow<List<Coin>>


}