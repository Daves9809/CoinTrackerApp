package com.example.cointracker.data.db

import androidx.room.*
import com.example.cointracker.data.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin)

    @Query("Select * FROM coin_table")
    fun getSavedCoins(): Flow<List<Coin>>

    @Delete
    suspend fun deleteCoin(coin: Coin)
}