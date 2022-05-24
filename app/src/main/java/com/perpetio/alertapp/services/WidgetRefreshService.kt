package com.perpetio.alertapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.repository.ApiError
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import com.perpetio.alertapp.utils.Formatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class WidgetRefreshService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startOnForeground()
        val repository = Repository(getAlertApiService())
        CoroutineScope(Job()).launch {
            val statesInfo = try {
                repository.refreshStates()
            } catch (e: ApiError) {
                StatesInfoModel(
                    emptyList(),
                    Formatter.getShortFormat(Date())
                )
            }
            WidgetUpdateReceiver.checkUpdate(
                statesInfo, this@WidgetRefreshService
            )
            stopSelf()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startOnForeground() {
        val channelId = getString(R.string.widget_service_channel_id)
        val notificationId = getString(R.string.widget_service_notification_id).toInt()
        createNotificationChannel(channelId)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.widget_service_notification_title))
            .setContentText(getString(R.string.widget_service_notification_text))
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(notificationId, notification.build())
    }

    private fun createNotificationChannel(channelId: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.widget_service_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                channelId, channelName, importance
            )
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}