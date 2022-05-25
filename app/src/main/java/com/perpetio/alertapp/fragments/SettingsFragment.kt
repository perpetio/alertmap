package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.core.view.forEach
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.RepeatInterval
import com.perpetio.alertapp.databinding.FragmentSettingsBinding
import com.perpetio.alertapp.receivers.WidgetRefreshReminder
import com.perpetio.alertapp.utils.Formatter

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        showSettings()
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
                rgRepeatInterval.visibility = getVisibility(isChecked)
                enableSaving(true)
            }
            rgRepeatInterval.setOnCheckedChangeListener { radioGroup, buttonId ->
                enableSaving(true)
            }
            chkNotification.setOnCheckedChangeListener { button, isChecked ->
                val visibility = getVisibility(isChecked)
                chkNotificationSound.visibility = visibility
                tvTerritoryTitle.visibility = visibility
                tvTerritory.visibility = visibility
            }
            tvTerritory.setOnClickListener {
                Log.d("123", "Open territories dialog")
            }
            btnSave.setOnClickListener {
                saveSettings()
                enableSaving(false)
            }
        }
    }

    private fun enableSaving(value: Boolean) {
        val titleText = getString(R.string.settings)
        binding.apply {
            tvTitle.text = if (value) "${titleText}*" else titleText
            btnSave.visibility = getVisibility(value)
        }
    }

    private fun showSettings() {
        binding.apply {
            app.storage.apply {
                repeatInterval?.let { repeatInterval ->
                    val intervals = RepeatInterval.values().toList()
                    intervals.find { interval ->
                        interval.minutes == repeatInterval
                    }?.let { interval ->
                        rgRepeatInterval.check(interval.btnId)
                    }
                    chkAutoUpdate.isChecked = true
                }
                nextUpdateTime?.let { time ->
                    tvNextUpdate.text = "(${Formatter.getTimeFormat(time)})"
                }
            }
        }
        enableSaving(false)
    }

    private fun saveSettings() {
        binding.apply {
            if (chkAutoUpdate.isChecked) {
                val checkedButtonId = rgRepeatInterval.checkedRadioButtonId
                val intervals = RepeatInterval.values().toList()
                intervals.find { interval ->
                    interval.btnId == checkedButtonId
                }?.let { interval ->
                    val time = WidgetRefreshReminder.startWithDelay(
                        interval.minutes, requireContext()
                    )
                    app.storage.apply {
                        repeatInterval = interval.minutes
                        nextUpdateTime = time
                    }
                    tvNextUpdate.text = "(${Formatter.getTimeFormat(time)})"
                }
            } else {
                WidgetRefreshReminder.cancel(requireContext())
                app.storage.apply {
                    repeatInterval = null
                    nextUpdateTime = null
                }
                tvNextUpdate.text = ""
            }
        }
    }
}