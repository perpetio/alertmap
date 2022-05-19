package com.perpetio.alertapp.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import com.perpetio.alertapp.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    fun refreshMap() {
        withLoading {
            val statesInfo = repository.refreshStates()
            _state.value = ViewModelState.MapLoaded(statesInfo)
        }
    }

    private fun withLoading(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
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
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }
}