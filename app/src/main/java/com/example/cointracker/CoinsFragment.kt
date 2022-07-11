package com.example.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
    }

    private fun viewCoinList() {
        viewModel.getCoinsFromAPI()
        viewModel.coins.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        coinsAdapter.differ.submitList(it.data?.toList())
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
}