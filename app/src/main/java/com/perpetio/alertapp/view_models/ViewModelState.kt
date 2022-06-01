package com.perpetio.alertapp.view_models

import com.perpetio.alertapp.data_models.StateModel

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class Notification(val changeList: List<StateModel>) : ViewModelState()
    data class Error(val message: String?) : ViewModelState()
    object Completed : ViewModelState()
}
