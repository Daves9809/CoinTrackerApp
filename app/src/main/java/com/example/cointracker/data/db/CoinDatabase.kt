package com.example.cointracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.util.converters.Converters
import com.example.cointracker.data.util.converters.JsonParser

@Database(
    entities = [Coin::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CoinDatabase() : RoomDatabase() {

    abstract fun getCoinDAO(): CoinDAO

}