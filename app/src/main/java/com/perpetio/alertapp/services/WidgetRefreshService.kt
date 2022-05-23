package com.perpetio.alertapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.repository.ApiError
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class WidgetRefreshService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startOnForeground()
        Log.d("123", "Service start...")
        val repository = Repository(getAlertApiService())
        CoroutineScope(Job()).launch {
            val statesInfo = try {
                repository.refreshStates()
            } catch (e: ApiError) {
                StatesInfoModel(emptyList(), "")
            }
            WidgetUpdateReceiver.checkUpdate(statesInfo.states, this@WidgetRefreshService)
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

    private fun startOnForeground() {
        val channelId = "Foreground Service ID"
        val notificationId = 101
        createNotificationChannel(channelId)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Service enabled")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(notificationId, notification.build())
    }

    private fun createNotificationChannel(channelId: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.map_service_channel)
            val channelDescription = getString(R.string.map_service_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                channelId, channelName, importance
            ).apply {
                description = channelDescription
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}