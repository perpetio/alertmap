package com.perpetio.alertapp.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Vibrator(
    private val context: Context
) {
    private val vibrator: Vibrator

    init {
        vibrator = initVibrator()
    }

    fun vibrate(
        times: Int,
        duration: Int,
        delay: Int,
    ) {
        CoroutineScope(Job()).launch {
            for (i in 1..times) {
                vibrate(duration)
                delay(delay.toLong())
            }
        }
    }

    private fun vibrate(duration: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrateApiO(duration)
        } else vibrateApi(duration)
    }

    @Suppress("DEPRECATION")
    private fun vibrateApi(duration: Int) {
        vibrator.vibrate(duration.toLong())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrateApiO(duration: Int) {
        val effect = VibrationEffect.createOneShot(
            duration.toLong(), VibrationEffect.DEFAULT_AMPLITUDE
        )
        vibrator.vibrate(effect)
    }

    private fun initVibrator(): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
}