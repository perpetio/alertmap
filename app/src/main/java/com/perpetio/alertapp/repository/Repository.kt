package com.perpetio.alertapp.repository

import com.perpetio.alertapp.data_models.StatesInfoModel

class Repository(
    private val airAlertApi: AirAlertApi
) {
    suspend fun refreshStates(): StatesInfoModel {
        val response = airAlertApi.getStates()
        response.apply {
            data?.let { statesInfo ->
                return statesInfo
            }
            error?.message?.let { message ->
                throw ApiError(message)
            }
        }
        throw UnknownError
    }
}

class ApiError(message: String) : Exception(message)
object UnknownError : Exception()