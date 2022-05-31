package com.perpetio.alertapp.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.doubleArgViewModelFactory
import com.perpetio.alertapp.data.LocalStorage
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(
    private val storage: LocalStorage,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    private val _statesInfo = MutableLiveData<StatesInfoModel>()
    val statesInfo: LiveData<StatesInfoModel> = _statesInfo

    private val _refreshTime = MutableLiveData<Long>()
    val refreshTime: LiveData<Long> = _refreshTime

    private var refreshJob: Job? = null

    fun refreshMap() {
        viewModelScope.launch {
            withLoading {
                handleData(repository.refreshStates())
            }
        }
    }

    fun refreshMapPeriodically(
        minutesInterval: Int
    ) {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                _refreshTime.value = WidgetRefreshReminder.getNextTime(minutesInterval)
                delay(minutesInterval * 60 * 1000L)
                withLoading {
                    handleData(repository.refreshStates())
                }
            }
        }
    }

    fun cancelMapRefreshing() {
        refreshJob?.cancel()
        _refreshTime.value = 0
    }

    private fun handleData(statesInfo: StatesInfoModel) {
        _statesInfo.value = statesInfo

        repository.isAirAlert(
            statesInfo.states,
            storage.observedStatesId,
            storage.minutesRepeatInterval
        )?.let { isAlert ->
            _state.value = ViewModelState.AirAlert(isAlert)
        }
    }

    private suspend fun withLoading(
        block: suspend () -> Unit
    ) {
        try {
            _state.value = ViewModelState.Loading
            block()
        } catch (e: Exception) {
            _state.value = ViewModelState.Error(e.message)
        } finally {
            _state.value = ViewModelState.Completed
        }
    }

    companion object {
        val FACTORY = doubleArgViewModelFactory(::MainViewModel)
    }
}