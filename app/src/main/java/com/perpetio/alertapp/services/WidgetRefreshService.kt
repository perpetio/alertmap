package com.perpetio.alertapp.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.perpetio.alertapp.AlertApp
import com.perpetio.alertapp.R
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
            val statesInfo = refreshStates(repository)
            val changeList = repository.getChangeList(
                statesInfo.states,
                app.storage.observedStatesId,
                app.storage.minutesRepeatInterval
            )
            if (changeList.isNotEmpty()) {
                NotificationPublisher(this@WidgetRefreshService).showChangeList(
                    changeList, app.storage.soundCheck, app.storage.vibrationCheck
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

    private suspend fun refreshStates(
        repository: Repository
    ): StatesInfoModel {
        return try {
            val freshInfo = repository.refreshStates()
            app.storage.statesInfo = freshInfo
            freshInfo
        } catch (e: ApiError) {
            StatesInfoModel(
                emptyList(),
                Date()
            )
        }
    }

    private fun isFresh(
        stateInfo: StatesInfoModel?
    ): Boolean {
        stateInfo?.refreshTime?.let { refreshTime ->
            return Formatter.isDateFresh(
                refreshTime,
                app.storage.minutesRepeatInterval
            )
        }
        return false
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