package com.example.cointracker.data.repository.dataSourceImpl

import com.example.cointracker.data.db.CoinDAO
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.repository.dataSource.CoinsLocalDataSource
import kotlinx.coroutines.flow.Flow

class CoinsLocalDataSourceImpl(
    private val coinDAO: CoinDAO
): CoinsLocalDataSource {
    override suspend fun saveCoinToDB(coin: Coin) {
        coinDAO.insert(coin)
    }

    override fun getSavedCoinsFromDB(): Flow<List<Coin>> {
        return coinDAO.getSavedCoins()
    }

    override suspend fun deleteCoin(coin: Coin) {
        coinDAO.deleteCoin(coin)
    }
}