package com.perpetio.alertapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity
import com.perpetio.alertapp.data_models.StateModel


class NotificationPublisher(
    private val context: Context
) {
    private val alertSound: Uri =
        Uri.parse(
            "android.resource://"
                    + context.packageName + "/" + R.raw.horn
        );

    private val channelId by lazy { context.getString(R.string.notification_channel_id) }
    private val channelName by lazy { context.getString(R.string.notification_channel_name) }

    fun showChangeList(
        changeList: List<StateModel>,
        makeSound: Boolean,
        vibrate: Boolean
    ) {
        val title = context.getString(R.string.attention_x2)
        val alert = context.getString(R.string.alert)
        val noAlert = context.getString(R.string.no_alert)
        val content = changeList.joinToString(
            separator = ";\n", postfix = "."
        ) { state ->
            "${state.name} - ${if (state.isAlert) alert else noAlert}"
        }
        val notificationId = context.getString(R.string.alert_notification_id).toInt()
        NotificationPublisher(context).showNotification(
            notificationId, title, content, makeSound, vibrate
        )
    }

    fun buildNotification(
        title: String,
        content: String,
        withSound: Boolean,
        withOpenAppLogic: Boolean
    ): Notification {

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .apply {
                if (withSound) setSound(alertSound)
                if (withOpenAppLogic) setContentIntent(getOpenAppIntent())
            }.build()
    }

    private fun showNotification(
        id: Int,
        title: String,
        content: String,
        withSound: Boolean,
        vibrate: Boolean
    ) {
        createNotificationChannel()
        val notification = buildNotification(
            title, content, withSound, true
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)

        if (vibrate) {
            Vibrator(context).vibrate(3, 50, 50)
        }
    }

    private fun getOpenAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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