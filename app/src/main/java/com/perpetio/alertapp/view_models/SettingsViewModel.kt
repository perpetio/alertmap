package com.perpetio.alertapp.view_models

import androidx.lifecycle.ViewModel
import com.perpetio.alertapp.data.RepeatInterval
import java.util.*

class SettingsViewModel : ViewModel() {
    var isDataLoaded = false
    var isDataSaved = true
    var autoUpdateCheck = false
    var repeatInterval = RepeatInterval.Min.minutes
    var notificationCheck = false
    var soundCheck = false
    var vibrationCheck = false
    var observedStatesId = mutableListOf<Int>()
}