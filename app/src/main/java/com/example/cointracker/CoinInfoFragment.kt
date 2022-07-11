package com.example.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.cointracker.data.model.Coin
import com.example.cointracker.databinding.FragmentCoinInfoBinding
import com.example.cointracker.databinding.FragmentCoinsBinding
import com.example.cointracker.presentation.viewModel.CoinsViewModel
import java.math.BigInteger


class CoinInfoFragment : Fragment() {

    private var _binding: FragmentCoinInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CoinsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val args: CoinInfoFragmentArgs by navArgs()
        val coin = args.selectedCoin
        bindData(coin)
    }

    fun bindData(coin: Coin) {
        binding.apply {
            tvInfoCoinName.text = coin.name
            coinCMCRank.text = coin.cmcRank.toString()
            coin.quote?.uSD?.let {
                percent1HChange.text = "${viewModel.roundDoubleTo4PlacesAfterComa(it.percentChange1h!!)}%"
                percent24HChange.text = "${viewModel.roundDoubleTo4PlacesAfterComa(it.percentChange24h!!)}%"
                percentWeekChange.text = "${viewModel.roundDoubleTo4PlacesAfterComa(it.percentChange7d!!)}%"
                coinPrice.text = "${viewModel.roundDoubleTo2PlacesAfterComa(it.price!!)}$"
                lastUpdated.text = viewModel.formatHour(it.lastUpdated)
            }
            val tags: MutableList<String?> = mutableListOf()
            coin.tags?.toList()?.let { tags.addAll(it) }
            val string = tags.joinToString(", ")
            binding.tags.text = "$string"
        }
    }


}