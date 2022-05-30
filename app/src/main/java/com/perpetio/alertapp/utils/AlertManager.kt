package com.perpetio.alertapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.data_models.StateModel

class AlertManager(
    private val context: Context
) {
    private val alertSound: Uri =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    private val channelId by lazy {
        context.getString(R.string.widget_service_channel_id)
    }
    private val notificationId by lazy {
        context.getString(R.string.widget_service_notification_id).toInt()
    }

    private val pendingFlags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

    fun showAlert(states: List<StateModel>, context: Context) {
        val title = context.getString(R.string.alert_)
        val content = states.joinToString(
            separator = "\n", postfix = ".\n"
        ) + context.getString(R.string.go_to_the_refuge)

        createNotificationChannel()
        val notification = buildNotification(title, content, getOpenAppIntent())
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun buildNotification(
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): Notification {

        return NotificationCompat.Builder(context, channelId)
            .setSound(alertSound)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun getOpenAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, pendingFlags)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.widget_service_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                channelId, channelName, importance
            )
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}