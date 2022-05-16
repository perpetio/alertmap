package com.perpetio.alertapp.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.perpetio.alertapp.R
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.utils.MapDrawer
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository();
        val viewModel = ViewModelProvider(
            this, MainViewModel.FACTORY(repository)
        ).get(MainViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.fetchData()
            AlarmTimeManager.setReminder(this@MainActivity)
        }

        val mapHolder = findViewById<ImageView>(R.id.canvas_holder)

        viewModel.state.observe(this) { state ->
            when (state) {
                ViewModelState.Loading -> showProgress()
                is ViewModelState.MapLoaded -> {
                    hideProgress()
                    mapHolder.setImageBitmap(MapDrawer.drawMap(this))
                }
                is ViewModelState.Error -> showError(state.message)
            }
        }
    }
}