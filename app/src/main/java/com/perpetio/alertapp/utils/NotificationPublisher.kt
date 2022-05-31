package com.perpetio.alertapp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.perpetio.alertapp.R
import com.perpetio.alertapp.activities.MainActivity


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

    fun informUser(isAlert: Boolean, withSound: Boolean) {
        val title = context.getString(
            if (isAlert) R.string.air_alert_
            else R.string.air_alert_is_stopped
        )
        val content = context.getString(
            if (isAlert) R.string.go_to_the_refuge
            else R.string.get_back_to_business
        )
        val notificationId = context.getString(R.string.alert_notification_id).toInt()
        NotificationPublisher(context).showNotification(
            notificationId, title, content, withSound, true
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
            .setContentText(content)
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
        withOpenAppLogic: Boolean
    ) {
        createNotificationChannel()
        val notification = buildNotification(
            title, content, withSound, withOpenAppLogic
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
        vibrate()
    }

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        vibrator.vibrate(500)
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