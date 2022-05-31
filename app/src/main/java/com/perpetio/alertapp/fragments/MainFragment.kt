package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.databinding.FragmentMainBinding
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.utils.MapDrawer
import com.perpetio.alertapp.utils.NotificationPublisher
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun getViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

        if (viewModel.state.value == null) {
            viewModel.refreshMap()
        }
    }

    override fun onStart() {
        super.onStart()
        WidgetRefreshReminder.cancel(requireContext())
        if (app.storage.autoUpdateCheck) {
            viewModel.refreshMapPeriodically(app.storage.minutesRepeatInterval)
        }
    }

    override fun onStop() {
        if (app.storage.autoUpdateCheck) {
            val refreshTime = viewModel.refreshTime.value!!
            viewModel.cancelMapRefreshing()
            WidgetRefreshReminder.startAtTime(refreshTime, requireContext())
            app.storage.timeUpdate = refreshTime
        }
        super.onStop()
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.AirAlert -> showAlert(state.isAlert)
                is ViewModelState.Error -> showError(state.message)
                is ViewModelState.Completed -> hideProgress()
            }
        }
        viewModel.statesInfo.observe(viewLifecycleOwner) { statesInfo ->
            Log.d("123", "viewModel.statesInfo.observe: $statesInfo")
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

    private fun showAlert(isAlert: Boolean) {
        NotificationPublisher(requireContext()).informUser(
            isAlert, app.storage.notificationSoundCheck
        )
    }

    private fun showProgress() {
        binding.refreshLayout.isRefreshing = true
    }

    private fun hideProgress() {
        binding.refreshLayout.isRefreshing = false
    }
}