package com.example.cointracker.domain.useCases

import com.example.cointracker.data.model.Coin
import com.example.cointracker.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedCoinsUseCase(private val coinsRepository: CoinsRepository) {

    suspend fun execute() : Flow<List<Coin>> = coinsRepository.getSavedCoins()

}