package com.example.cointracker.presentation.di

import com.example.cointracker.data.repository.CoinsRepositoryImpl
import com.example.cointracker.data.repository.dataSource.CoinsRemoteDataSource
import com.example.cointracker.data.repository.dataSourceImpl.CoinsRemoteDataSourceImpl
import com.example.cointracker.domain.repository.CoinsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCoinsRepository(
        coinsRemoteDataSource: CoinsRemoteDataSource
    ) : CoinsRepository{
        return CoinsRepositoryImpl(coinsRemoteDataSource)
    }
}