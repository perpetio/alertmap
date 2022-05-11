package com.perpetio.alertapp.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import com.perpetio.alertapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(ViewModelState.Loading)
                _state.postValue(ViewModelState.MapLoaded(
                    repository.refreshMap()
                ))
            } catch (e: Exception) {
                _state.postValue(ViewModelState.Error(e.message))
            }
        }
    }

    companion object {
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }
}