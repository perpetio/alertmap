package com.perpetio.alertapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perpetio.alertapp.data_models.StatesInfoModel
import java.util.*

class LocalStorage(
    context: Context,
    private val gson: Gson
) {
    private val prefs = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    fun clear() {
        prefs.edit().clear().apply()
    }

    var autoUpdateCheck: Boolean
        get() = prefs.getBoolean(AUTOUPDATE, true)
        set(value) = prefs.edit().putBoolean(
            AUTOUPDATE, value
        ).apply()

    var minutesRepeatInterval: Int
        get() = prefs.getInt(REPEAT_INTERVAL, RepeatInterval.Min.minutes)
        set(value) {
            prefs.edit().putInt(
                REPEAT_INTERVAL, value
            ).apply()
        }

    var timeUpdate: Long
        get() = prefs.getLong(TIME_UPDATE, Date().time)
        set(value) {
            prefs.edit().putLong(
                TIME_UPDATE, value
            ).apply()
        }

    var notificationCheck: Boolean
        get() = prefs.getBoolean(NOTIFICATION_CHECK, false)
        set(value) = prefs.edit().putBoolean(
            NOTIFICATION_CHECK, value
        ).apply()

    var soundCheck: Boolean
        get() = prefs.getBoolean(SOUND_CHECK, false)
        set(value) = prefs.edit().putBoolean(
            SOUND_CHECK, value
        ).apply()

    var vibrationCheck: Boolean
        get() = prefs.getBoolean(VIBRATION_CHECK, false)
        set(value) = prefs.edit().putBoolean(
            VIBRATION_CHECK, value
        ).apply()

    var observedStatesId: List<Int>
        get() {
            val json = prefs.getString(OBSERVED_TERRITORIES, "")
            val listType = object : TypeToken<List<Int>>() {}.type
            return try {
                gson.fromJson(json, listType)
            } catch (e: Exception) {
                emptyList()
            }
        }
        set(value) {
            val json = gson.toJson(value)
            prefs.edit().putString(OBSERVED_TERRITORIES, json).apply()
        }

    var statesInfo: StatesInfoModel?
        get() {
            val json = prefs.getString(STATE_INFO, "")
            val listType = object : TypeToken<StatesInfoModel>() {}.type
            return try {
                gson.fromJson(json, listType)
            } catch (e: Exception) {
                null
            }
        }
        set(value) {
            val json = gson.toJson(value)
            prefs.edit().putString(STATE_INFO, json).apply()
        }

    companion object {
        private const val STORAGE_NAME = "alert_app_storage"

        private const val AUTOUPDATE = "autoupdate"
        private const val REPEAT_INTERVAL = "repeat_interval"
        private const val TIME_UPDATE = "time_update"

        private const val NOTIFICATION_CHECK = "notification_check"
        private const val SOUND_CHECK = "sound_check"
        private const val VIBRATION_CHECK = "vibration_check"
        private const val OBSERVED_TERRITORIES = "observed_territories"
        private const val STATE_INFO = "state_info"
    }
}