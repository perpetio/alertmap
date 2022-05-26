package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perpetio.alertapp.adapters.SelectTerritoryAdapter
import com.perpetio.alertapp.databinding.FragmentSelectTerritotyBinding
import com.perpetio.alertapp.view_models.MainViewModel

class SelectTerritoryFragment : BaseFragment<FragmentSelectTerritotyBinding>() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun getViewBinding(): FragmentSelectTerritotyBinding {
        return FragmentSelectTerritotyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.apply {
            val layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            rvTerritories.layoutManager = layoutManager
            viewModel.statesInfo.value?.states?.let { states ->
                rvTerritories.adapter = SelectTerritoryAdapter(states)
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                goTo(SelectTerritoryFragmentDirections.toMainFragment())
            }
        }
    }
}