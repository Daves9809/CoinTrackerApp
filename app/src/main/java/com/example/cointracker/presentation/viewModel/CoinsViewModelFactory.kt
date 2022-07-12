package com.example.cointracker.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cointracker.domain.useCases.DeleteCoinFromDBUseCase
import com.example.cointracker.domain.useCases.GetCoinsFromAPIUseCase
import com.example.cointracker.domain.useCases.GetSavedCoinsUseCase
import com.example.cointracker.domain.useCases.SaveCoinToDBUseCase

class CoinsViewModelFactory(
    val app: Application,
    val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase,
    val saveCoinsUseCase: SaveCoinToDBUseCase,
    val getSavedCoinsUseCase: GetSavedCoinsUseCase,
    val deleteCoinFromDBUseCase: DeleteCoinFromDBUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinsViewModel(
            app,
            getCoinsFromAPIUseCase,
            saveCoinsUseCase,
            getSavedCoinsUseCase,
            deleteCoinFromDBUseCase
        ) as T
    }
}