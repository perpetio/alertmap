package com.perpetio.alertapp.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.utils.Formatter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    private val _statesInfo = MutableLiveData<StatesInfoModel>()
    val statesInfo: LiveData<StatesInfoModel> = _statesInfo

    private var mapRefreshingJob: Job? = null

    fun refreshMap() {
        viewModelScope.launch {
            withLoading {
                val statesInfo = repository.refreshStates()
                _statesInfo.value = StatesInfoModel(
                    statesInfo.states,
                    Formatter.getShortFormat(Date())
                )
            }
        }
    }

    fun refreshMapPeriodically(
        minutesInterval: Int
    ) {
        mapRefreshingJob = viewModelScope.launch {
            while (isActive) {
                withLoading {
                    val statesInfo = repository.refreshStates()
                    _statesInfo.value = StatesInfoModel(
                        statesInfo.states,
                        Formatter.getShortFormat(Date())
                    )
                }
                delay(minutesInterval * 60 * 1000L)
            }
        }
    }

    fun cancelMapRefreshing() {
        mapRefreshingJob?.cancel()
    }

    private suspend fun withLoading(
        block: suspend () -> Unit
    ) {
        try {
            Log.d("123", "withLoading Loading")
            _state.value = ViewModelState.Loading
            block()
        } catch (e: Exception) {
            Log.d("123", "withLoading Exception: $e")
            _state.value = ViewModelState.Error(e.message)
        } finally {
            Log.d("123", "withLoading Completed")
            _state.value = ViewModelState.Completed
        }
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }
}