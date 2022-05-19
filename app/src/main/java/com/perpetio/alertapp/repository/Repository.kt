package com.perpetio.alertapp.repository

import android.util.Log
import com.perpetio.alertapp.data_models.StatesInfoModel

class Repository(
    private val airAlertApi: AirAlertApi
) {
    suspend fun refreshStates(): StatesInfoModel {
        try {
            Log.d("123", "refreshStates start")
            return airAlertApi.getStates()
        } catch (cause: Throwable) {
            throw ApiError("Can't load states", cause)
        }
    }
}

class ApiError(message: String, cause: Throwable) : Exception(message, cause)