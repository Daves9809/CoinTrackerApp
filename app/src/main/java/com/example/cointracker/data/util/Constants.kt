package com.example.cointracker.data.util

import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.Quote
import com.example.cointracker.data.model.USD

class Constants {
    companion object{
        val urlForImageLoading: String = "https://s2.coinmarketcap.com/static/img/coins/64x64/"

        val coinsToAdd: List<Coin> = listOf(
            Coin(1.0,1,"",1,
                "",1L,"bitcoin",
                Quote(USD("1",1.0,3.0,4.0,5.0,6.0)),"btc",
                "symbol", listOf("dd","pb"),100.0
            ),
            Coin(2.0,2,"",2,
                "",2L,"ethereum",
                Quote(USD("2",2.0,34.0,5.0,7.0,8.0)),"eth",
                "symbol2", listOf("ddes","pbqwe"),101.0
            )
        )
        val coinsInDB: List<Coin> = listOf(
            Coin(1.0,1,"",1,
                "",1L,"bitcoin",
                Quote(USD("1",4242.0,55433.0,14.0,25.0,653256.0)),"btc",
                "symbol", listOf("dd","pb"),100.0
            )
        )
    }
}