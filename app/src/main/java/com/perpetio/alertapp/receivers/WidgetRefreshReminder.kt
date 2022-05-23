package com.perpetio.alertapp.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.services.WidgetRefreshService
import java.text.SimpleDateFormat
import java.util.*

class WidgetRefreshReminder : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("123", "Alarm receiver onReceive")
        val serviceIntent = Intent(context, WidgetRefreshService::class.java)
        context.startService(serviceIntent)
        Log.d("123", "Alarm receiver started service")
        getApp(context).storage.repeatInterval?.let { interval ->
            startWithDelay(interval, context)
        }
    }

    private fun getApp(context: Context): AlertApp {
        return context.applicationContext as AlertApp
    }

    companion object {
        fun startWithDelay(delay: Int, context: Context) {
            Log.d("123", "Set reminder")
            setReceiverState(
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                context
            )

            val pendingIntent = getReminderPendingIntent(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                getNextRefreshTime(delay),
                pendingIntent
            )
        }

        fun cancel(context: Context) {
            Log.d("123", "Cancel reminder")
            setReceiverState(
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                context
            )

            val pendingIntent = getReminderPendingIntent(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }

        private fun setReceiverState(state: Int, context: Context) {
            val receiver = ComponentName(context, WidgetRefreshReminder::class.java)
            context.packageManager.setComponentEnabledSetting(
                receiver,
                state,
                PackageManager.DONT_KILL_APP
            )
        }

        private fun getNextRefreshTime(delay: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, delay)

            val dateFormat = SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault())
            val dateTime = Date(calendar.timeInMillis)
            Log.d("123", "Time: ${dateFormat.format(dateTime)}")

            return calendar.timeInMillis
        }

        private fun getReminderPendingIntent(context: Context): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, WidgetRefreshReminder::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}