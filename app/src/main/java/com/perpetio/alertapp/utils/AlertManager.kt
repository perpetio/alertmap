package com.perpetio.alertapp.utils

import android.content.ContentResolver
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.perpetio.alertapp.R

object AlertManager {

    private var alertSound: Ringtone? = null

    fun vibrate(
        context: Context
    ) {
        Vibrator(context).vibrate(1, 1000, 0)
    }

    fun playSound(
        context: Context
    ) {
        val sound = if (alertSound == null) {
            getAlertSound(context)
        } else alertSound

        sound?.play()
        alertSound = sound
    }

    private fun getAlertSound(
        context: Context
    ): Ringtone {
        val path = "${
            ContentResolver.SCHEME_ANDROID_RESOURCE
        }://${context.packageName}/${R.raw.alert_horn}"

        return RingtoneManager.getRingtone(
            context.applicationContext,
            Uri.parse(path)
        )
    }
}