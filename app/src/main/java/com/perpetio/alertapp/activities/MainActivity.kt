package com.perpetio.alertapp.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.databinding.ActivityMainBinding
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import com.perpetio.alertapp.utils.MapDrawer
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository(getAlertApiService())
        val viewModel = ViewModelProvider(
            this, MainViewModel.FACTORY(repository)
        ).get(MainViewModel::class.java)

        setupObservers(viewModel)
        setupListeners(viewModel)

        if (viewModel.state.value == null) {
            viewModel.refreshMap()
        }
    }

    private fun setupObservers(viewModel: MainViewModel) {
        viewModel.state.observe(this) { state ->
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.Error -> showError(state.message)
                is ViewModelState.Completed -> hideProgress()
            }
        }
        viewModel.statesInfo.observe(this) { statesInfo ->
            updateMap(statesInfo)
            WidgetUpdateReceiver.checkUpdate(
                statesInfo, this
            )
        }
    }

    private fun setupListeners(
        viewModel: MainViewModel
    ) {
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
                    statesInfo.states, this@MainActivity
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