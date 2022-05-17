package com.perpetio.alertapp.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.perpetio.alertapp.databinding.ActivityMainBinding
import com.perpetio.alertapp.repository.Repository
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

        val repository = Repository();
        val viewModel = ViewModelProvider(
            this, MainViewModel.FACTORY(repository)
        ).get(MainViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.fetchData()
            AlarmTimeManager.setReminder(this@MainActivity)
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.MapLoaded -> {
                    hideProgress()
                    updateMap()
                }
                is ViewModelState.Error -> showError(state.message)
            }
        }
    }

    private fun updateMap() {
        binding.apply {
            tvRefreshDate.text = Formatter.getShortFormat(Date())
            imgMapHolder.setImageBitmap(MapDrawer.drawMap(this@MainActivity))
        }
    }
}