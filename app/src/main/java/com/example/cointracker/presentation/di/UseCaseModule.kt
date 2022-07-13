package com.example.cointracker.presentation.di

import com.example.cointracker.domain.repository.CoinsRepository
import com.example.cointracker.domain.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetCoinsFromAPIUseCase(
        coinsRepository: CoinsRepository
    ) : GetCoinsFromAPIUseCase = GetCoinsFromAPIUseCase(coinsRepository)

    @Singleton
    @Provides
    fun provideGetSavedCoinsUseCase(
        coinsRepository: CoinsRepository
    ) : GetSavedCoinsUseCase = GetSavedCoinsUseCase(coinsRepository)

    @Singleton
    @Provides
    fun provideSaveCoinsToDBUseCase(
        coinsRepository: CoinsRepository
    ) : SaveCoinToDBUseCase = SaveCoinToDBUseCase(coinsRepository)

    @Singleton
    @Provides
    fun provideDeleteCoinFromDBUseCase(
        coinsRepository: CoinsRepository
    ) : DeleteCoinFromDBUseCase = DeleteCoinFromDBUseCase(coinsRepository)

    @Singleton
    @Provides
    fun provideUpdateCoinsUseCase(
        coinsRepository: CoinsRepository
    ) : UpdateCoinsUseCase = UpdateCoinsUseCase(coinsRepository)

}