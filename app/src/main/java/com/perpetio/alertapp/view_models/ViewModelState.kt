package com.perpetio.alertapp.view_models

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class Error(val message: String?) : ViewModelState()
    object Completed : ViewModelState()
}
