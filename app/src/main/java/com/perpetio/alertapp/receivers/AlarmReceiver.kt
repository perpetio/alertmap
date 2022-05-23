package com.perpetio.alertapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.services.MapRefreshService
import com.perpetio.alertapp.utils.AlarmTimeManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("123", "Alarm receiver onReceive")
        context?.apply {
            Intent(this, MapRefreshService::class.java).also {
                startService(it)
            }
            Log.d("123", "Alarm receiver started service")
            getApp(this).storage.repeatInterval?.let { interval ->
                AlarmTimeManager.setReminder(interval, this)
            }
        }
    }

    private fun getApp(context: Context): AlertApp {
        return context.applicationContext as AlertApp
    }
}