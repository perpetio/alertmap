package com.perpetio.alertapp.activities

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.databinding.ActivityMainBinding
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.Formatter
import com.perpetio.alertapp.utils.MapDrawer
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

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
        viewModel.refreshMap()
        AlarmTimeManager.setReminder(this@MainActivity)
        Log.d("123", "MainActivity onCreate end")
    }

    private fun setupObservers(viewModel: MainViewModel) {
        viewModel.state.observe(this) { state ->
            Log.d("123", "viewModel.state.observe $state")
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.MapLoaded -> updateMap(state.statesInfo)
                is ViewModelState.Error -> showError(state.message)
                is ViewModelState.Completed -> hideProgress()
            }
        }
    }

    private fun updateMap(statesInfo: StatesInfoModel) {
        Log.d("123", "updateMap: $statesInfo")
        binding.apply {
            tvRefreshDate.text = Formatter.getShortFormat(Date())
            imgMapHolder.setImageBitmap(
                MapDrawer.drawMap(
                    statesInfo.states, this@MainActivity
                )
            )
        }
    }
}