package com.example.cointracker.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.cointracker.data.db.CoinDatabase
import com.example.cointracker.data.util.converters.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context, converters: Converters) =
        Room.inMemoryDatabaseBuilder(context, CoinDatabase::class.java)
            .addTypeConverter(converters)
            .allowMainThreadQueries()
            .build()



}