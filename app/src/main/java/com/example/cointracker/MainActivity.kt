package com.example.cointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cointracker.databinding.ActivityMainBinding
import com.example.cointracker.presentation.adapter.CoinsAdapter
import com.example.cointracker.presentation.viewModel.CoinsViewModel
import com.example.cointracker.presentation.viewModel.CoinsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory : CoinsViewModelFactory
    @Inject
    lateinit var coinsAdapter: CoinsAdapter
    lateinit var viewModel : CoinsViewModel
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvMainActivity.setupWithNavController(navController)

        viewModel = ViewModelProvider(this,factory).get(CoinsViewModel::class.java)
    }
}