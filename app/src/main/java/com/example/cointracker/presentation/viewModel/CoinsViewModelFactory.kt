package com.example.cointracker.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cointracker.domain.useCases.*

class CoinsViewModelFactory(
    val app: Application,
    val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase,
    val saveCoinsUseCase: SaveCoinToDBUseCase,
    val getSavedCoinsUseCase: GetSavedCoinsUseCase,
    val deleteCoinFromDBUseCase: DeleteCoinFromDBUseCase,
    val updateCoinsUseCase: UpdateCoinsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinsViewModel(
            app,
            getCoinsFromAPIUseCase,
            saveCoinsUseCase,
            getSavedCoinsUseCase,
            deleteCoinFromDBUseCase,
            updateCoinsUseCase
        ) as T
    }
}