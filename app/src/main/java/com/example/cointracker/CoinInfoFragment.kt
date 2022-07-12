package com.example.cointracker

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.cointracker.data.model.Coin
import com.example.cointracker.databinding.FragmentCoinInfoBinding
import com.example.cointracker.databinding.FragmentCoinsBinding
import com.example.cointracker.presentation.viewModel.CoinsViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.math.BigInteger
import kotlin.math.roundToInt


class CoinInfoFragment : Fragment() {

    private var _binding: FragmentCoinInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CoinsViewModel
    private lateinit var obtainedCoin: Coin

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
        obtainedCoin = args.selectedCoin
        bindData(obtainedCoin)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabFollow.setOnClickListener {
            Toast.makeText(activity,"You have added coin ${obtainedCoin.name} to Saved Coins",Toast.LENGTH_SHORT).show()
            viewModel.saveCoin(obtainedCoin)
        }
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
        setUpDoughnutChart(coin)
    }

    private fun setUpDoughnutChart(coin: Coin){
        val pieChart = binding.pieChart

        val circulatingSupply = coin.circulatingSupply?.toFloat()
        val maxSupply = coin.maxSupply?.toFloat()

        if(circulatingSupply != null && maxSupply != null){
            val dataEntries = mutableListOf<PieEntry>()
            dataEntries.add(PieEntry(maxSupply-circulatingSupply, "remaining supply"))
            dataEntries.add(PieEntry(circulatingSupply, "circulating supply"))

            val colors = arrayListOf(Color.parseColor("#3FD727"), Color.parseColor("#E52020"))
            val set = PieDataSet(dataEntries, "")
            val data = PieData(set)
            val percentageProgress = (circulatingSupply / maxSupply * 100.0)
            set.valueTextColor = Color.BLACK
            set.colors = colors
            pieChart.data = data
            pieChart.data.setValueTextColor(Color.BLACK)
            pieChart.description.text = ""
            pieChart.centerText = "${percentageProgress.roundToInt()}%"
            pieChart.legend.textColor = Color.parseColor("#FFFFFF")
            pieChart.legend.textSize = 14f
            pieChart.invalidate() // refresh
        }
    }


}