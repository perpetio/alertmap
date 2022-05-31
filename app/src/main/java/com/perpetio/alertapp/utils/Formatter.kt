package com.perpetio.alertapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    fun getShortFormat(dateTime: Date): String {
        return SimpleDateFormat(
            "dd MMMM, HH:mm", Locale.getDefault()
        ).format(dateTime)
    }

    fun getTimeFormat(time: Long): String {
        return SimpleDateFormat(
            "HH:mm", Locale.getDefault()
        ).format(Date(time))
    }

    fun getDate(dateTime: String): Date? {
        return try {
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()
            ).parse(dateTime) //2022-05-31T13:19:38+03:00
        } catch (e: Exception) {
            return null
        }
    }

    fun isDateFresh(dateTime: Date, maxInterval: Long): Boolean {
        return (Date().time - dateTime.time) < maxInterval
    }
}