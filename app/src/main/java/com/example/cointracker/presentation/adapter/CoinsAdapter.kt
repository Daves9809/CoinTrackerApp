package com.example.cointracker.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cointracker.data.model.Coin
import com.example.cointracker.data.util.Constants
import com.example.cointracker.databinding.CoinListItemBinding
import kotlin.math.roundToInt

class CoinsAdapter : RecyclerView.Adapter<CoinsAdapter.CoinsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val binding = CoinListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.bind(coin)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CoinsViewHolder(
        val binding: CoinListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: Coin) {
            val percentageChange24h = roundValue(coin.quote?.uSD?.percentChange24h!!)
            val coinPrice = roundValue(coin.quote.uSD.price!!)

            binding.tvCoinSymbol.text = coin.symbol
            binding.tvCoinName.text = coin.name
            binding.tvRank.text = coin.cmcRank.toString()
            binding.tvCoinValue.text = "$$coinPrice"
            binding.tvVolume24HChange.text = "$percentageChange24h%"
            if (percentageChange24h >= 0) {
                binding.tvVolume24HChange.setTextColor(Color.GREEN)
            } else
                binding.tvVolume24HChange.setTextColor(Color.RED)

            val imageUrl = Constants.urlForImageLoading + coin.id + ".png"
            Glide.with(binding.ivCoinIcon.context)
                .load(imageUrl)
                .into(binding.ivCoinIcon)

            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(coin)
                }
            }
        }
    }

    fun roundValue(double: Double): Double {
        return (double * 100.0).roundToInt() / 100.0
    }

    private var onItemClickListener: ((Coin) -> Unit)? = null

    fun setOnItemClickListener(listener : (Coin)->Unit){
        onItemClickListener = listener
    }
}
