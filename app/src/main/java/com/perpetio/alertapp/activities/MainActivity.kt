package com.perpetio.alertapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.screens.ErrorScreen
import com.perpetio.alertapp.screens.LoadingScreen
import com.perpetio.alertapp.screens.MapScreen
import com.perpetio.alertapp.ui.theme.AlertAppTheme
import com.perpetio.alertapp.utils.AlarmTimeManager
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.ViewModelState

class MainActivity : ComponentActivity() {
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

        setContent {
            AlertAppTheme {
                App(viewModel)
            }
        }
    }
}

@Composable
fun App(
    viewModel: MainViewModel
) {
    viewModel.state.observeAsState().value?.let { state ->
        when(state) {
            ViewModelState.Loading -> LoadingScreen()
            is ViewModelState.MapLoaded -> MapScreen()
            is ViewModelState.Error -> ErrorScreen(
                message = state.message
            )
        }
    }
}