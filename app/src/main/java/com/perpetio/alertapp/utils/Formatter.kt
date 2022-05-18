package com.perpetio.alertapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    fun getShortFormat(dateTime: Date): String {
        return SimpleDateFormat(
            "dd MMMM, HH:mm", Locale.getDefault()
        ).format(dateTime)
    }

    fun getShortFormat(time: String): String {
        return try {
            val dateTime = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault()
            ).parse(time)!!
            getShortFormat(dateTime)
        } catch (e: Exception) {
            ""
        }
    }
}