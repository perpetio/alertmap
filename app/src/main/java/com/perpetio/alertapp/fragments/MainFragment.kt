package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.perpetio.alertapp.data_models.StateModel
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.databinding.FragmentMainBinding
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.utils.Formatter
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
    }

    override fun onStart() {
        super.onStart()
        WidgetRefreshReminder.cancel(requireContext())
        if (app.storage.autoUpdateCheck) {
            viewModel.refreshMapPeriodically(app.storage.minutesRepeatInterval)
        }
        viewModel.refreshMap()
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
                is ViewModelState.Notification -> notifyUser(state.changeList)
                is ViewModelState.Error -> showError(state.message)
                is ViewModelState.Completed -> hideProgress()
            }
        }
        viewModel.statesInfo.observe(viewLifecycleOwner) { statesInfo ->
            Log.d("123", "viewModel.statesInfo.observe: $statesInfo")
            refreshMap(statesInfo)
            WidgetUpdateReceiver.checkUpdate(
                statesInfo, requireContext()
            )
        }
    }

    private fun setupListeners() {
        binding.apply {
            imgMapHolder.setOnClickListener {
                NotificationPublisher(requireContext()).showChangeList(
                    listOf(), app.storage.soundCheck, app.storage.vibrationCheck
                )
            }
            refreshLayout.setOnRefreshListener {
                viewModel.refreshMapForce()
            }
        }
    }

    private fun refreshMap(statesInfo: StatesInfoModel) {
        binding.apply {
            tvRefreshDate.text = Formatter.getShortFormat(statesInfo.refreshTime!!)
            imgMapHolder.setImageBitmap(
                MapDrawer.drawMap(
                    statesInfo.states, requireContext()
                )
            )
        }
    }

    private fun notifyUser(changeList: List<StateModel>) {
        if (changeList.isNotEmpty()) {
            NotificationPublisher(requireContext()).showChangeList(
                changeList, app.storage.soundCheck, app.storage.vibrationCheck
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