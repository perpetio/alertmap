package com.perpetio.alertapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.perpetio.alertapp.receivers.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

object AlarmTimeManager {

    private const val ALARM_INTERVAL = 1 // 5 min

    fun setReminder(context: Context) {
        Log.d("123", "setReminder")
        setReceiverState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED,  context)

        val pendingIntent = getReminderPendingIntent(context)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            getNextRefreshTime(),
            pendingIntent
        )
    }

    fun cancelReminder(context: Context) {
        Log.d("123", "cancelReminder")
        setReceiverState(PackageManager.COMPONENT_ENABLED_STATE_DISABLED, context)

        val pendingIntent = getReminderPendingIntent(context)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun setReceiverState(state: Int, context: Context) {
        val receiver = ComponentName(context, AlarmReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            state,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun getNextRefreshTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, ALARM_INTERVAL)

        val dateFormat = SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH)
        val dateTime = Date(calendar.timeInMillis)
        Log.d("123", "Next refresh time: ${dateFormat.format(dateTime)}")

        return calendar.timeInMillis
    }

    private fun getReminderPendingIntent(context: Context): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}