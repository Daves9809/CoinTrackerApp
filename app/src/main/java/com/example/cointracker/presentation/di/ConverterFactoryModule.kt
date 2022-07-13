package com.example.cointracker.presentation.di

import com.example.cointracker.data.util.converters.Converters
import com.example.cointracker.data.util.converters.GsonParser
import com.example.cointracker.data.util.converters.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConverterFactoryModule {

    @Singleton
    @Provides
    fun provideCoverters(jsonParser: JsonParser) : Converters {
        return Converters(jsonParser)
    }
}