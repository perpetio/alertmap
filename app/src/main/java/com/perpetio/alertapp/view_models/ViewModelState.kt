package com.perpetio.alertapp.view_models

import com.perpetio.alertapp.data_models.DistrictState

sealed class ViewModelState {
    object Loading : ViewModelState()
    data class MapLoaded(val list: List<DistrictState>) : ViewModelState()
    data class Error(val message: String?) : ViewModelState()
}
