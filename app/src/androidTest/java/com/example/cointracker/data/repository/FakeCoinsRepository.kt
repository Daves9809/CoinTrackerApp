package com.example.cointracker.data.repository

import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Constants
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCoinsRepository: CoinsRepository {

    private val coinList = mutableListOf<Coin>()

    private val observableCoinList: Flow<List<Coin>> = flow{
        emit(coinList)
    }

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getCoinsFromAPI(): Resource<CoinList> {
        return if(shouldReturnNetworkError){
            Resource.Error("No internet connection", null)
        }else
            Resource.Success(CoinList(Constants.coinsToAdd,null))
    }

    override suspend fun saveCoinToDB(coin: Coin) {
        coinList.add(coin)
    }

    override suspend fun deleteCoin(coin: Coin) {
        coinList.remove(coin)
    }

    override fun getSavedCoins(): Flow<List<Coin>> {
        return observableCoinList
    }

    override suspend fun updateCoins(coins: List<Coin>) {
        for(coin in coins){
            val coinToDelete = coinList.filter { it.id == coin.id }[0]
            coinList.remove(coinToDelete)
        }
        coinList.addAll(coins)
    }
}