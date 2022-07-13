package com.example.cointracker.presentation.di

import android.app.Application
import com.example.cointracker.domain.useCases.*
import com.example.cointracker.presentation.viewModel.CoinsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideCoinsViewModelFactory(
        app: Application,
        getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase,
        saveCoinsUseCase: SaveCoinToDBUseCase,
        getSavedCoinsUseCase: GetSavedCoinsUseCase,
        deleteCoinFromDBUseCase: DeleteCoinFromDBUseCase,
        updateCoinsUseCase: UpdateCoinsUseCase
    ) : CoinsViewModelFactory {
        return CoinsViewModelFactory(
            app,
            getCoinsFromAPIUseCase,
            saveCoinsUseCase,
            getSavedCoinsUseCase,
            deleteCoinFromDBUseCase,
            updateCoinsUseCase
        )
    }

}