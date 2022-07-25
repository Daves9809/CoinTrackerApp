package com.example.cointracker.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.cointracker.MainCoroutineRule
import com.example.cointracker.data.repository.FakeCoinsRepository
import com.example.cointracker.data.util.Constants
import com.example.cointracker.data.util.Resource
import com.example.cointracker.data.util.Status
import com.example.cointracker.domain.useCases.*
import com.example.cointracker.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CoinsViewModelTest{

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CoinsViewModel

    val fakeCoinsRepository: FakeCoinsRepository = FakeCoinsRepository()

    //Use cases
    private val getCoinsFromAPIUseCase: GetCoinsFromAPIUseCase = GetCoinsFromAPIUseCase(fakeCoinsRepository)
    private val saveCoinsUseCase: SaveCoinToDBUseCase = SaveCoinToDBUseCase(fakeCoinsRepository)
    private val getSavedCoinsUseCase: GetSavedCoinsUseCase = GetSavedCoinsUseCase(fakeCoinsRepository)
    private val deleteCoinFromDBUseCase: DeleteCoinFromDBUseCase = DeleteCoinFromDBUseCase(fakeCoinsRepository)
    private val updateCoinsUseCase: UpdateCoinsUseCase = UpdateCoinsUseCase(fakeCoinsRepository)

    @Before
    fun setUp() {
        viewModel = CoinsViewModel(
            ApplicationProvider.getApplicationContext(),
            getCoinsFromAPIUseCase,
            saveCoinsUseCase,
            getSavedCoinsUseCase,
            deleteCoinFromDBUseCase,
            updateCoinsUseCase
        )
    }

    @Test
    fun roundDoubleTo2PlacesAfterComa_validInput_returnsValid() {
        val value = viewModel.roundDoubleTo2PlacesAfterComa(54.44444)
        assertThat(value).isEqualTo(54.44)
    }
    @Test
    fun roundDoubleTo4PlacesAfterComa_validInput_returnsValid() {
        val value = viewModel.roundDoubleTo4PlacesAfterComa(54.66666666)
        assertThat(value).isEqualTo(54.6667)
    }
    @Test
    fun formatHour_validInput_validHour(){
        val hour = "2022-07-20T16:25:00 000Z"
        val formattedHour = "2022-07-20 at 16:25:00"
        assertThat(viewModel.formatHour(hour)).isEqualTo(formattedHour)
    }
    @Test
    fun getSavedCoins_saveOneCoin_databaseIsNotEmpty(){
        val coin = Constants.coinsToAdd.first()
        viewModel.saveCoin(coin)
        val savedCoin = viewModel.getSavedCoins().getOrAwaitValue()

        assertThat(savedCoin.isNotEmpty())
    }

    @Test
    fun saveCoinToDB_saveOneCoin_validSaving() {
        viewModel.saveCoin(Constants.coinsToAdd[0])
        val savedCoin = viewModel.getSavedCoins().getOrAwaitValue().first()

        assertThat(savedCoin.id).isEqualTo(Constants.coinsToAdd[0].id)
    }

    @Test
    fun deleteCoinFromDB_validCoin_validDelete() {
        viewModel.saveCoin(Constants.coinsToAdd[0])
        viewModel.deleteCoin(Constants.coinsToAdd[0])
        val listOfCoins = viewModel.getSavedCoins().getOrAwaitValue()

        assertThat(listOfCoins.isEmpty())
    }

    @Test
    fun updateCoinsInDB_updateOneCoin_validUpdating(){
        val apiCoins = Constants.coinsToAdd
        val coinsInDB = Constants.coinsInDB
        coinsInDB.forEach { viewModel.saveCoin(it) }
        viewModel.updateCoins(apiCoins,coinsInDB)
        val updatedCoins = apiCoins.filter { coinsInDB.map { it.cmcRank }.contains(it.cmcRank) }

        assertThat(updatedCoins.size).isEqualTo(coinsInDB.size)
    }

    @Test
    fun getCoinsFromAPI_InputConnectionAvailable_ReturnSucces(){
        viewModel.getCoinsFromAPI(true)
        val value = viewModel.coins.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun getCoinsFromAPI_InputConnectionIsNotAvailable_ReturnError(){
        viewModel.getCoinsFromAPI(false)
        val value = viewModel.coins.getOrAwaitValue()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}