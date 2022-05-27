package com.perpetio.alertapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.perpetio.alertapp.data_models.StateModel
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
        get() = prefs.getBoolean(AUTOUPDATE, false)
        set(value) = prefs.edit().putBoolean(
            AUTOUPDATE, value
        ).apply()

    var repeatInterval: Int
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
        get() = prefs.getBoolean(NOTIFY, false)
        set(value) = prefs.edit().putBoolean(
            NOTIFY, value
        ).apply()

    var notificationSoundCheck: Boolean
        get() = prefs.getBoolean(NOTIFY_WITH_SOUND, false)
        set(value) = prefs.edit().putBoolean(
            NOTIFY_WITH_SOUND, value
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

    companion object {
        private const val STORAGE_NAME = "alert_app_storage"

        private const val AUTOUPDATE = "autoupdate"
        private const val REPEAT_INTERVAL = "repeat_interval"
        private const val TIME_UPDATE = "time_update"

        private const val NOTIFY = "notify"
        private const val NOTIFY_WITH_SOUND = "notify_with_sound"
        private const val OBSERVED_TERRITORIES = "observed_territories"
    }
}