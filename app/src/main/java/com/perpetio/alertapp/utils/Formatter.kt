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
}