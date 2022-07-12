package com.example.cointracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cointracker.databinding.FragmentCoinsBinding
import com.example.cointracker.databinding.FragmentSavedCoinsBinding
import com.example.cointracker.presentation.adapter.CoinsAdapter
import com.example.cointracker.presentation.viewModel.CoinsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedCoinsFragment : Fragment() {
    private lateinit var viewModel: CoinsViewModel
    private lateinit var coinsAdapter: CoinsAdapter
    private var _binding: FragmentSavedCoinsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedCoinsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        coinsAdapter = (activity as MainActivity).coinsAdapter
        initRecyclerView()
        viewModel.getSavedCoins().observe(viewLifecycleOwner, Observer {
            coinsAdapter.differ.submitList(it)
        })
        coinsAdapter.setOnItemClickListener {
            Log.i("MYTAG", "coin = ${it}")
            val bundle: Bundle = Bundle().apply {
                putSerializable("selected_coin", it)
            }
            findNavController().navigate(
                R.id.action_savedCoinsFragment_to_coinInfoFragment,
                bundle
            )
        }

        //functionality for deleting objects by swiping
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val coin = coinsAdapter.differ.currentList[position]
                viewModel.deleteCoin(coin)
                Snackbar.make(view,"Deleted Succesfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo"){
                            viewModel.saveCoin(coin)
                        }
                        show()
                    }
            }

        }

        //synchronizing recyclerView with itemtouchhelper
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvSavedCoins)
        }
    }

    private fun initRecyclerView() {
        binding.rvSavedCoins.apply {
            adapter = coinsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}