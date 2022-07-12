package com.example.cointracker.presentation.di

import com.example.cointracker.data.util.converters.GsonParser
import com.example.cointracker.data.util.converters.JsonParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JsonModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideJsonParser(gson: Gson): JsonParser {
        return GsonParser(gson)
    }

}