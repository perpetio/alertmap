package com.perpetio.alertapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    fun getShortFormat(date: Date): String {
        return SimpleDateFormat(
            "dd MMMM, HH:mm", Locale.getDefault()
        ).format(date)
    }
}