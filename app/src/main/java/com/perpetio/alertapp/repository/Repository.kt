package com.perpetio.alertapp.repository

import android.util.Log
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
        observedStatesId: List<Int>,
        minutesRefreshInterval: Int
    ): List<StateModel> {
        Log.d("123", "getAlertList states: $states")
        Log.d("123", "observedStatesId: $observedStatesId")
        val result = mutableListOf<StateModel>()
        observedStatesId.forEach { stateId ->
            states.find { state ->
                state.id == stateId
            }?.let { state ->
                Log.d("123", "forEach state: $state")
                Formatter.getDate(state.updateTime)?.let { refreshDate ->
                    Log.d("123", "state refreshDate: $refreshDate")
                    val interval = minutesRefreshInterval * 60 * 1000L
                    if (Formatter.isDateFresh(refreshDate, interval)) {
                        Log.d("123", "DateFresh: $refreshDate")
                        result.add(state)
                    } else Log.d("123", "Date is old")
                }
            }
        }
        Log.d("123", "getAlertList result: $result")
        return result
    }

}

class ApiError(message: String, cause: Throwable) : Exception(message, cause)