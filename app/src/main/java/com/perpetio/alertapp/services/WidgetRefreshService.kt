package com.perpetio.alertapp.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data_models.StateModel
import com.perpetio.alertapp.data_models.StatesInfoModel
import com.perpetio.alertapp.receivers.WidgetUpdateReceiver
import com.perpetio.alertapp.repository.ApiError
import com.perpetio.alertapp.repository.Repository
import com.perpetio.alertapp.repository.getAlertApiService
import com.perpetio.alertapp.utils.Formatter
import com.perpetio.alertapp.utils.NotificationPublisher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class WidgetRefreshService : Service() {

    private val app by lazy {
        application as AlertApp
    }

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
            repository.getAlertList(
                statesInfo.states,
                app.storage.observedStatesId
            ).let {
                if (it.isNotEmpty()) {
                    notifyUser(it)
                }
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
        NotificationPublisher(this).apply {
            createNotificationChannel()
            val notificationId = getString(R.string.refresh_service_notification_id).toInt()
            val notification = buildNotification(
                getString(R.string.refresh_service_notification_title),
                getString(R.string.refresh_service_notification_content),
                false, false
            )
            startForeground(notificationId, notification)
        }
    }

    private fun notifyUser(states: List<StateModel>) {
        val title = getString(R.string.air_alert_)
        val content = states.joinToString(
            separator = "\n", postfix = ".\n"
        ) + getString(R.string.go_to_the_refuge)
        val withSound = app.storage.notificationSoundCheck

        NotificationPublisher(this).showNotification(
            getString(R.string.alert_notification_id).toInt(),
            title, content, withSound, true
        )
    }

    companion object {
        fun startSelf(context: Context) {
            val serviceIntent = Intent(context, WidgetRefreshService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        }
    }
}