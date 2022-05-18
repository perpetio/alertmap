package com.perpetio.alertapp.view_models

import com.perpetio.alertapp.data_models.StatesInfoModel

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class MapLoaded(val statesInfo: StatesInfoModel) : ViewModelState()
    data class Error(val message: String?) : ViewModelState()
    object Completed: ViewModelState()
}
