package com.perpetio.alertapp.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpetio.alertapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = ViewModelState.Loading
                _state.value = ViewModelState.MapLoaded(
                    repository.refreshMap()
                )
            } catch (e: Exception) {
                _state.value = ViewModelState.Error(e.message)
            }
        }
    }

    companion object {
        const val SOURCE_URL = "https://alarmmap.online/"//"https://alerts.in.ua/"
        const val DISTRICT = "data-oblast"
        const val SECURITY_STATUS = "data-alert-type"

        const val AIR_RAID = "air-raid"
    }
}