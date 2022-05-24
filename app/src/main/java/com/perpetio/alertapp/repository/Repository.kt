package com.perpetio.alertapp.repository

import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.utils.Formatter
import java.util.*

class Repository(
    private val airAlertApi: AirAlertApi
) {
    suspend fun refreshStates(): StatesInfoModel {
        try {
            val statesInfo = airAlertApi.getStates()
            return StatesInfoModel(
                statesInfo.states,
                Formatter.getShortFormat(Date())
            )
        } catch (cause: Throwable) {
            throw ApiError("Can't load states", cause)
        }
    }
}

class ApiError(message: String, cause: Throwable) : Exception(message, cause)