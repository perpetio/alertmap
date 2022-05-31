package com.perpetio.alertapp.view_models

import com.perpetio.alertapp.data_models.StateModel

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class AirAlert(val isAlert: Boolean) : ViewModelState()
    data class Error(val message: String?) : ViewModelState()
    object Completed : ViewModelState()
}
