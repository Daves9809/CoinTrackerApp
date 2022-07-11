package com.example.cointracker.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cointracker.domain.useCases.GetCoinsFromAPIUseCase

class CoinsViewModelFactory(
    val app: Application,
    val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinsViewModel(app,getCoinsFromAPIUseCase) as T
    }
}