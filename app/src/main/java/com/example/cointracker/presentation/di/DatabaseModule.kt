package com.example.cointracker.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.cointracker.data.db.CoinDAO
import com.example.cointracker.data.db.CoinDatabase
import com.example.cointracker.data.util.converters.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCoinsDatabase(converters: Converters, app: Application): CoinDatabase {
        return Room.databaseBuilder(app,CoinDatabase::class.java,"coins_db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinsDao(coinDatabase: CoinDatabase): CoinDAO{
        return coinDatabase.getCoinDAO()
    }

}