package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perpetio.alertapp.adapters.SelectStateAdapter
import com.perpetio.alertapp.data_models.StateModel
import com.perpetio.alertapp.databinding.FragmentSelectStateBinding
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.SettingsViewModel

class SelectStateFragment : BaseFragment<FragmentSelectStateBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val settingsViewModel by activityViewModels<SettingsViewModel>()

    override fun getViewBinding(): FragmentSelectStateBinding {
        return FragmentSelectStateBinding.inflate(layoutInflater)
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
            rvStates.layoutManager = layoutManager
            rvStates.adapter = SelectStateAdapter(
                getStates(),
                object : SelectStateAdapter.CheckChangeListener {
                    override fun onCheckChange(stateId: Int, isChecked: Boolean) {
                        settingsViewModel.apply {
                            if (isChecked) {
                                if (!observedStatesId.contains(stateId)) {
                                    observedStatesId.add(stateId)
                                }
                            } else observedStatesId.remove(stateId)
                            isDataSaved = false
                        }
                    }
                }
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            btnClearChoice.setOnClickListener {
                val adapter = rvStates.adapter as SelectStateAdapter
                adapter.clearChoice()
            }
            btnBack.setOnClickListener {
                goTo(SelectStateFragmentDirections.toMainFragment())
            }
        }
    }

    private fun getStates(): List<StateModel> {
        val states = mainViewModel.statesInfo.value?.states?.map { state ->
            state.isChecked = settingsViewModel.observedStatesId.find { id ->
                id == state.id
            }?.let { true } ?: false
            state
        }
        return states ?: emptyList()
    }
}