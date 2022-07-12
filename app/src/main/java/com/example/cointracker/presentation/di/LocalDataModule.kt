package com.example.cointracker.presentation.di

import com.example.cointracker.data.db.CoinDAO
import com.example.cointracker.data.repository.dataSource.CoinsLocalDataSource
import com.example.cointracker.data.repository.dataSourceImpl.CoinsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideCoinsLocalDataSource(coinDAO: CoinDAO): CoinsLocalDataSource{
        return CoinsLocalDataSourceImpl(coinDAO)
    }

}