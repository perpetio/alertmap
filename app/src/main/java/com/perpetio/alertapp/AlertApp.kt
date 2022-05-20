package com.perpetio.alertapp

import android.app.Application
import com.google.gson.Gson
import com.perpetio.alertapp.data.LocalStorage

class AlertApp : Application() {

    private var _storage: LocalStorage? = null
    val storage get() = _storage!!

    override fun onCreate() {
        super.onCreate()
        _storage = LocalStorage(applicationContext, Gson())
    }
}