package com.example.cointracker.presentation.di

import com.example.cointracker.presentation.adapter.CoinsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideCoinsAdapter() : CoinsAdapter{
        return CoinsAdapter()
    }

}