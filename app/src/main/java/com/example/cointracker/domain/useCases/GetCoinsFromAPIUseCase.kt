package com.example.cointracker.domain.useCases

import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.repository.CoinsRepository

class GetCoinsFromAPIUseCase(private val coinsRepository: CoinsRepository) {

    suspend fun execute() : Resource<CoinList>{
        return coinsRepository.getCoinsFromAPI()
    }

}