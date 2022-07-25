package com.example.cointracker.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cointracker.R
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.util.Resource
import com.example.cointracker.databinding.FragmentCoinsBinding
import com.example.cointracker.presentation.adapter.CoinsAdapter
import com.example.cointracker.presentation.viewModel.CoinsViewModel

class CoinsFragment : Fragment() {
    private lateinit var viewModel: CoinsViewModel
    private lateinit var coinsAdapter: CoinsAdapter
    private var _binding: FragmentCoinsBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false
    private lateinit var coinsAPI : List<Coin>
    private lateinit var coinsDB: List<Coin>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        coinsAdapter = (activity as MainActivity).coinsAdapter
        initRecyclerView()
        viewCoinList()
        getCoinsDB()
        coinsAdapter.setOnItemClickListener {
            val bundle : Bundle = Bundle().apply {
                putSerializable("selected_coin",it)
            }
            findNavController().navigate(R.id.action_coinsFragment_to_coinInfoFragment,bundle)
        }
        refreshApp()
    }

    private fun getCoinsDB() {
        viewModel.getSavedCoins().observe(viewLifecycleOwner, Observer {
            coinsDB = it
        })
    }

    private fun viewCoinList() {
        viewModel.getCoinsFromAPI()
        viewModel.coins.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val list = it.data?.toList()
                        coinsAPI = list!!
                        coinsAdapter.differ.submitList(list)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = coinsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun refreshApp() {
        binding.swipeToRefreshCoinsFragment.setOnRefreshListener {
            Toast.makeText(activity, "Data refreshed!", Toast.LENGTH_SHORT).show()
            binding.swipeToRefreshCoinsFragment.isRefreshing = false
            viewModel.updateCoins(coinsAPI,coinsDB)
        }
    }
}