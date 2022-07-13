package com.example.cointracker.domain.useCases

import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.repository.CoinsRepository

class UpdateCoinsUseCase(private val coinsRepository: CoinsRepository) {

    suspend fun execute(coins: List<Coin>) {
        return coinsRepository.updateCoins(coins)
    }
}