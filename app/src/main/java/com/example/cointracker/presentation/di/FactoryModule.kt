package com.example.cointracker.presentation.di

import android.app.Application
import com.example.cointracker.domain.useCases.GetCoinsFromAPIUseCase
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
        getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase
    ) : CoinsViewModelFactory {
        return CoinsViewModelFactory(
            app,
            getCoinsFromAPIUseCase
        )
    }

}