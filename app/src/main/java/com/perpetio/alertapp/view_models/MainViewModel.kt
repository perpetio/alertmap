package com.perpetio.alertapp.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.doubleArgViewModelFactory
import com.perpetio.alertapp.data.LocalStorage
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.utils.Formatter
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

    fun refreshMapForce() {
        viewModelScope.launch {
            withLoading {
                val freshStatesInfo = repository.refreshStates()
                keepFreshData(freshStatesInfo)
            }
        }
    }

    fun refreshMap() {
        viewModelScope.launch {
            if (!isFresh(statesInfo.value)) {
                withLoading {
                    val freshStatesInfo = getFreshStates()
                    keepFreshData(freshStatesInfo)
                }
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
                if (!isFresh(statesInfo.value)) {
                    withLoading {
                        val freshStatesInfo = repository.refreshStates()
                        keepFreshData(freshStatesInfo)
                    }
                }
            }
        }
    }

    fun cancelMapRefreshing() {
        refreshJob?.cancel()
        _refreshTime.value = 0
    }

    private suspend fun getFreshStates(): StatesInfoModel {
        val storageStatesInfo = storage.statesInfo
        return if (!isFresh(storageStatesInfo)) {
            Log.d("123", "ViewModel storageStatesInfo is fresh false")
            repository.refreshStates()
        } else {
            Log.d("123", "ViewModel storageStatesInfo is fresh true")
            storageStatesInfo!!
        }
    }

    private fun keepFreshData(
        statesInfo: StatesInfoModel
    ) {
        storage.statesInfo = statesInfo
        val changeList = repository.getChangeList(
            statesInfo.states,
            storage.observedStatesId,
            storage.minutesRepeatInterval
        )
        _statesInfo.value = statesInfo
        _state.value = ViewModelState.Notification(changeList)
    }

    private fun isFresh(
        stateInfo: StatesInfoModel?
    ): Boolean {
        stateInfo?.refreshTime?.let { refreshTime ->
            val isFResh = Formatter.isDateFresh(
                refreshTime,
                storage.minutesRepeatInterval
            )
            return isFResh
        }
        return false
    }

    private suspend fun withLoading(
        block: suspend () -> Unit
    ) {
        try {
            _state.value = ViewModelState.Loading
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            _state.value = ViewModelState.Error(e.message)
        } finally {
            _state.value = ViewModelState.Completed
        }
    }

    companion object {
        val FACTORY = doubleArgViewModelFactory(::MainViewModel)
    }
}