package com.example.cointracker.domain.useCases

import com.example.cointracker.data.model.Coin
import com.example.cointracker.domain.repository.CoinsRepository

class DeleteCoinFromDBUseCase(private val coinsRepository: CoinsRepository) {

    suspend fun execute(coin: Coin) = coinsRepository.deleteCoin(coin)

}