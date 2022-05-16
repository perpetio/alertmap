package com.perpetio.alertapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import com.perpetio.alertapp.receivers.AlarmReceiver
import java.util.*

object AlarmTimeManager {

    private const val ALARM_INTERVAL = 5 // 5 min

    fun setReminder(context: Context) {
        setReceiverState(PackageManager.COMPONENT_ENABLED_STATE_ENABLED, context)

        val pendingIntent = getReminderPendingIntent(context)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            getNextRefreshTime(),
            pendingIntent
        )
    }

    fun cancelReminder(context: Context) {
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