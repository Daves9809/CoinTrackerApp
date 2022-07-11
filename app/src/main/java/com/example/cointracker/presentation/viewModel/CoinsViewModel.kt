package com.example.cointracker.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cointracker.data.model.CoinList
import com.example.cointracker.data.util.Resource
import com.example.cointracker.domain.useCases.GetCoinsFromAPIUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class CoinsViewModel(
    private val app: Application,
    private val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase
): AndroidViewModel(app) {

    val coins: MutableLiveData<Resource<CoinList>> = MutableLiveData()

    fun getCoinsFromAPI(){
        viewModelScope.launch {
            coins.postValue(Resource.Loading())
            try{
                if(isNetworkAvailable(app)){
                    val apiResult = getCoinsFromAPIUseCase.execute()
                    coins.postValue(apiResult)
                } else {
                    coins.postValue(Resource.Error("Internet is not connected"))
                }
            }catch (e: Exception){
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
}