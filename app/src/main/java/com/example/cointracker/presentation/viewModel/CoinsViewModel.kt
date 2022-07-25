package com.example.cointracker.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.useCases.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.roundToInt

class CoinsViewModel(
    private val app: Application,
    private val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase,
    private val saveCoinsUseCase: SaveCoinToDBUseCase,
    private val getSavedCoinsUseCase: GetSavedCoinsUseCase,
    private val deleteCoinFromDBUseCase: DeleteCoinFromDBUseCase,
    private val updateCoinsUseCase: UpdateCoinsUseCase
) : AndroidViewModel(app) {

    val coins: MutableLiveData<Resource<CoinList>> = MutableLiveData()


    fun getCoinsFromAPI(isNetworkAvailable: Boolean = isNetworkAvailable(app)) {
        viewModelScope.launch {
            coins.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable) {
                    val apiResult = getCoinsFromAPIUseCase.execute()
                    coins.postValue(apiResult)

                } else {
                    coins.postValue(Resource.Error("Internet is not connected"))
                }
            } catch (e: Exception) {
                coins.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    //boilerplate code to copy paste
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

    fun formatHour(lastUpdated: String?): String {
        return lastUpdated?.replace("T", " at ")?.dropLast(5) ?: "Update's hour not found"
    }

    fun roundDoubleTo2PlacesAfterComa(double: Double): Double =
        (double * 100.0).roundToInt() / 100.0


    fun roundDoubleTo4PlacesAfterComa(double: Double): Double =
        (double * 10000.0).roundToInt() / 10000.0


    //local data - room

    fun saveCoin(coin: Coin) = viewModelScope.launch {
        saveCoinsUseCase.execute(coin)
    }

    fun getSavedCoins() = liveData {
        getSavedCoinsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteCoin(coin: Coin) = viewModelScope.launch {
        deleteCoinFromDBUseCase.execute(coin)
    }

    fun updateCoins(listAPICoins: List<Coin>, listDBCoins: List<Coin>) = viewModelScope.launch {
        val coinsToUpdate =
            listAPICoins.filter { listDBCoins.map { it.cmcRank }.contains(it.cmcRank) }
        updateCoinsUseCase.execute(coinsToUpdate)
    }

}