package com.perpetio.alertapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.perpetio.alertapp.receivers.MapWidgetReceiver
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapRefreshService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("123", "Service start...")
        val repository = Repository(getAlertApiService())
        CoroutineScope(Job()).launch {
            val statesInfo = repository.refreshStates()
            MapWidgetReceiver.checkUpdate(this@MapRefreshService)
            Log.d("123", "Service end...")
            stopSelf()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d("123", "Service onDestroy")
        super.onDestroy()
    }
}