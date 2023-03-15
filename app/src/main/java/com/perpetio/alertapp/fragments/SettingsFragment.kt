package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.RepeatInterval
import com.perpetio.alertapp.databinding.FragmentSettingsBinding
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.utils.AlertManager
import com.perpetio.alertapp.utils.Formatter
import com.perpetio.alertapp.view_models.MainViewModel
import com.perpetio.alertapp.view_models.SettingsViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val settingsViewModel by activityViewModels<SettingsViewModel>()

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        loadSettings()
        showSettings()
        setupObservers()
        setupListeners()
    }

    private fun setupViews() {
        binding.apply {
            val intervals = RepeatInterval.values().toList()
            rgRepeatInterval.forEach { button ->
                intervals.find { interval ->
                    interval.btnId == button.id
                }?.let { interval ->
                    (button as RadioButton).text = getString(R.string.min, interval.minutes)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            chkAutoUpdate.setOnCheckedChangeListener { button, isChecked ->
                showAutoUpdateUi(isChecked)
                settingsViewModel.autoUpdateCheck = isChecked
                chkNotification.isChecked = false
                enableSaving(true)
            }
            rgRepeatInterval.setOnCheckedChangeListener { radioGroup, buttonId ->
                val intervals = RepeatInterval.values().toList()
                val interval = intervals.find { it.btnId == buttonId }!!.minutes

                settingsViewModel.repeatInterval = interval
                enableSaving(true)
            }
            chkNotification.setOnCheckedChangeListener { button, isChecked ->
                showNotificationUi(isChecked)
                settingsViewModel.notificationCheck = isChecked
                enableSaving(true)
            }
            chkSound.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) AlertManager.playSound(requireContext())
                settingsViewModel.soundCheck = isChecked
                enableSaving(true)
            }
            chkVibration.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) AlertManager.vibrate(requireContext())
                settingsViewModel.vibrationCheck = isChecked
                enableSaving(true)
            }
            btnSelectStates.setOnClickListener {
                goTo(MainFragmentDirections.toSelectStateFragment())
            }
            btnSave.setOnClickListener {
                saveSettings()
                enableSaving(false)
            }
        }
    }

    private fun setupObservers() {
        mainViewModel.refreshTime.observe(viewLifecycleOwner) { refreshTime ->
            binding.tvRefreshTime.text = if (refreshTime > 0L) {
                "(${Formatter.getTimeFormat(refreshTime)})"
            } else ""
        }
    }

    private fun enableSaving(value: Boolean) {
        val titleText = getString(R.string.settings)
        binding.apply {
            tvTitle.text = if (value) "${titleText}*" else titleText
            btnSave.visibility = getVisibility(value)
        }
        settingsViewModel.isDataSaved = !value
    }

    private fun showAutoUpdateUi(value: Boolean) {
        binding.apply {
            val visibility = getVisibility(value)
            rgRepeatInterval.visibility = visibility
            chkNotification.visibility = visibility
        }
    }

    private fun showNotificationUi(value: Boolean) {
        binding.apply {
            val visibility = getVisibility(value)
            chkSound.visibility = visibility
            chkVibration.visibility = visibility
            btnSelectStates.visibility = visibility
        }
    }

    private fun loadSettings() {
        val storage = app.storage
        settingsViewModel.apply {
            if (!isDataLoaded) {
                autoUpdateCheck = storage.autoUpdateCheck
                repeatInterval = storage.minutesRepeatInterval
                notificationCheck = storage.notificationCheck
                soundCheck = storage.soundCheck
                vibrationCheck = storage.vibrationCheck
                observedStatesId = storage.observedStatesId.toMutableList()
                isDataSaved = true
                isDataLoaded = true
            }
        }
    }

    private fun showSettings() {
        binding.apply {
            settingsViewModel.apply {
                chkAutoUpdate.isChecked = autoUpdateCheck
                showAutoUpdateUi(autoUpdateCheck)
                val intervals = RepeatInterval.values().toList()
                intervals.find { interval ->
                    interval.minutes == repeatInterval
                }?.let { interval ->
                    rgRepeatInterval.check(interval.btnId)
                }
                chkNotification.isChecked = notificationCheck
                chkSound.isChecked = soundCheck
                chkVibration.isChecked = vibrationCheck
                showNotificationUi(autoUpdateCheck && notificationCheck)
                enableSaving(!isDataSaved)
            }
        }
    }

    private fun saveSettings() {
        val settings = settingsViewModel
        val storage = app.storage

        if (settings.autoUpdateCheck) {
            mainViewModel.refreshMapPeriodically(
                WidgetRefreshReminder.getNextTime(settings.repeatInterval),
                settings.repeatInterval
            )
        } else mainViewModel.cancelMapRefreshing()
        storage.apply {
            autoUpdateCheck = settings.autoUpdateCheck
            minutesRepeatInterval = settings.repeatInterval
            notificationCheck = settings.notificationCheck
            soundCheck = settings.soundCheck
            vibrationCheck = settings.vibrationCheck
            observedStatesId = settings.observedStatesId
        }
    }
}