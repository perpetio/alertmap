package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.databinding.FragmentMapBinding
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.utils.MapDrawer
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState

class MapFragment : BaseFragment<FragmentMapBinding>() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun getViewBinding(): FragmentMapBinding {
        return FragmentMapBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

        if (viewModel.state.value == null) {
            viewModel.refreshMap()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.Error -> showError(state.message)
                is ViewModelState.Completed -> hideProgress()
            }
        }
        viewModel.statesInfo.observe(viewLifecycleOwner) { statesInfo ->
            updateMap(statesInfo)
            WidgetUpdateReceiver.checkUpdate(
                statesInfo, requireContext()
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                viewModel.refreshMap()
            }
            btnSettings.setOnClickListener {
                goTo(MapFragmentDirections.toSettingsFragment())
            }
        }
    }

    private fun updateMap(statesInfo: StatesInfoModel) {
        binding.apply {
            tvRefreshDate.text = statesInfo.refreshTime
            imgMapHolder.setImageBitmap(
                MapDrawer.drawMap(
                    statesInfo.states, requireContext()
                )
            )
        }
    }

    private fun showProgress() {
        binding.refreshLayout.isRefreshing = true
    }

    private fun hideProgress() {
        binding.refreshLayout.isRefreshing = false
    }
}