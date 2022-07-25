package com.example.cointracker.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.model.Quote
import com.example.cointracker.data.model.USD
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class CoinDAOTest{

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: CoinDatabase
    lateinit var dao: CoinDAO

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.getCoinDAO()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCoinItem() = runTest {
        val coin = Coin(1.0,1,"",1,
            "",1L,"bitcoin",
            Quote(USD("1",1.0,3.0,4.0,5.0,6.0)),"btc",
            "symbol", listOf("dd","pb"),100.0
        )
        dao.insert(coin)

        val allCoins = dao.getSavedCoins().first()[0]

        assertThat(allCoins).isEqualTo(coin)
    }

    @Test
    fun deleteCoinItem() = runTest {
        val coin = Coin(1.0,1,"",1,
            "",1L,"bitcoin",
            Quote(USD("1",1.0,3.0,4.0,5.0,6.0)),"btc",
            "symbol", listOf("dd","pb"),100.0
        )
        dao.insert(coin)
        dao.deleteCoin(coin)

        val allCoins = dao.getSavedCoins().first()

        assertThat(allCoins.isEmpty())
    }
}