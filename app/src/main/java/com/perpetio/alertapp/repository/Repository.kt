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

    fun isAirAlert(
        states: List<StateModel>,
        observedStatesId: List<Int>,
        minutesRefreshInterval: Int
    ): Boolean? {
        var isNewChanges = false
        var isAlert = false
        observedStatesId.forEach { stateId ->
            states.find { state ->
                state.id == stateId
            }?.let { state ->
                Formatter.getDate(state.updateTime)?.let { refreshDate ->
                    val interval = minutesRefreshInterval * 60 * 1000L
                    if (Formatter.isDateFresh(refreshDate, interval)) {
                        isNewChanges = true
                        if (state.isAlert) isAlert = true
                    }
                }
            }
        }
        return if (isNewChanges) {
            isAlert
        } else null
    }

}

class ApiError(message: String, cause: Throwable) : Exception(message, cause)