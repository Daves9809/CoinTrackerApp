package com.example.cointracker.presentation.di

import com.example.cointracker.data.api.CoinAPIService
import com.example.cointracker.data.repository.dataSource.CoinsRemoteDataSource
import com.example.cointracker.data.repository.dataSourceImpl.CoinsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCoinsRemoteDataSource(coinAPIService: CoinAPIService): CoinsRemoteDataSource {
        return CoinsRemoteDataSourceImpl(coinAPIService)
    }

}