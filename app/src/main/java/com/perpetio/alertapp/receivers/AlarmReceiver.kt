package com.perpetio.alertapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.perpetio.alertapp.utils.AlarmTimeManager

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("123", "On alarm receive")
        context?.apply {
            refreshMap(this)
        }
    }

    private fun refreshMap(context: Context) {
        val refreshIntent = MapWidgetReceiver.getRefreshIntent(context)
        context.sendBroadcast(refreshIntent)
    }
}