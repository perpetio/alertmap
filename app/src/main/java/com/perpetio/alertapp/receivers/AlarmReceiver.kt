package com.perpetio.alertapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            refreshMap(this)
        }
    }

    private fun refreshMap(context: Context) {
        val refreshIntent = MapWidgetReceiver.getRefreshIntent(context)
        context.sendBroadcast(refreshIntent)
    }
}