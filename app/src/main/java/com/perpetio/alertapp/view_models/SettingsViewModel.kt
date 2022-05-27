package com.perpetio.alertapp.view_models

import androidx.lifecycle.ViewModel
import com.perpetio.alertapp.data.RepeatInterval
import com.perpetio.alertapp.data_models.StateModel
import java.util.*

class SettingsViewModel : ViewModel() {
    var isDataLoaded = false
    var isDataSaved = true
    var autoUpdateCheck = false
    var repeatInterval = RepeatInterval.Min.minutes
    var timeUpdate = Date().time
    var notificationCheck = false
    var notificationSoundCheck = false
    var observedTerritories = listOf<StateModel>()
}