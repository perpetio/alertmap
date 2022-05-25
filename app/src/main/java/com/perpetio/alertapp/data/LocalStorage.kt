package com.perpetio.alertapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class LocalStorage(
    context: Context,
    private val gson: Gson
) {
    private val prefs = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    fun clear() {
        prefs.edit().clear().apply()
    }

    var repeatInterval: Int?
        get() {
            val interval = prefs.getInt(REPEAT_INTERVAL, NULL_INT)
            return if (interval == NULL_INT) null else interval
        }
        set(value) {
            prefs.edit().putInt(
                REPEAT_INTERVAL, value ?: NULL_INT
            ).apply()
        }

    var nextUpdateTime: Long?
        get() {
            val time = prefs.getLong(NEXT_UPDATE_TIME, NULL_LONG)
            return if (time == NULL_LONG) null else time
        }
        set(value) {
            prefs.edit().putLong(
                NEXT_UPDATE_TIME, value ?: NULL_LONG
            ).apply()
        }

    var shouldNotify: Boolean
        get() = prefs.getBoolean(SHOULD_NOTIFY, false)
        set(value) = prefs.edit().putBoolean(
            SHOULD_NOTIFY, value
        ).apply()

    var notifyWithSound: Boolean
        get() = prefs.getBoolean(NOTIFY_WITH_SOUND, false)
        set(value) = prefs.edit().putBoolean(
            NOTIFY_WITH_SOUND, value
        ).apply()

    var observedTerritories: List<String>
        get() {
            val json = prefs.getString(OBSERVED_TERRITORIES, "")
            val listType = object : TypeToken<List<String>>() {}.type
            return try {
                gson.fromJson(json, listType)
            } catch (e: JsonSyntaxException) {
                emptyList()
            }
        }
        set(value) {
            val json = gson.toJson(value)
            prefs.edit().putString(OBSERVED_TERRITORIES, json).apply()
        }

    companion object {
        private const val STORAGE_NAME = "alert_app_storage"
        private const val REPEAT_INTERVAL = "repeat_interval"
        private const val NEXT_UPDATE_TIME = "next_update_time"

        private const val SHOULD_NOTIFY = "should_notify"
        private const val NOTIFY_WITH_SOUND = "notify_with_sound"
        private const val OBSERVED_TERRITORIES = "observed_territories"

        private const val NULL_INT = -1
        private const val NULL_LONG = -1L
    }
}