package com.perpetio.alertapp.repository

import com.perpetio.alertapp.data_models.StateModel
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

    fun getAlertList(
        states: List<StateModel>,
        observedStatesId: List<Int>
    ): List<StateModel> {
        val result = mutableListOf<StateModel>()
        observedStatesId.forEach { stateId ->
            states.find { state ->
                state.id == stateId && state.isAlert
            }?.let { state ->
                result.add(state)
            }
        }
        return result
    }
}

class ApiError(message: String, cause: Throwable) : Exception(message, cause)