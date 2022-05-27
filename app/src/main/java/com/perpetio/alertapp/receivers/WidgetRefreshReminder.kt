package com.perpetio.alertapp.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.services.WidgetRefreshService
import java.util.*

class WidgetRefreshReminder : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        WidgetRefreshService.startSelf(context)
        getApp(context).storage.apply {
            if (autoUpdateCheck) {
                timeUpdate = startWithDelay(repeatInterval, context)
            }
        }
    }

    private fun getApp(context: Context): AlertApp {
        return context.applicationContext as AlertApp
    }

    companion object {
        fun startWithDelay(delay: Int, context: Context): Long {
            val refreshTime = getNextTime(delay)
            return startAtTime(refreshTime, context)
        }

        fun startAtTime(time: Long, context: Context): Long {
            setReceiverState(
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                context
            )

            val pendingIntent = getReminderPendingIntent(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
            return time
        }

        fun cancel(context: Context) {
            setReceiverState(
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                context
            )

            val pendingIntent = getReminderPendingIntent(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }

        fun getNextTime(delay: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, delay)
            return calendar.timeInMillis
        }

        private fun setReceiverState(state: Int, context: Context) {
            val receiver = ComponentName(context, WidgetRefreshReminder::class.java)
            context.packageManager.setComponentEnabledSetting(
                receiver,
                state,
                PackageManager.DONT_KILL_APP
            )
        }

        private fun getReminderPendingIntent(context: Context): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, WidgetRefreshReminder::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}