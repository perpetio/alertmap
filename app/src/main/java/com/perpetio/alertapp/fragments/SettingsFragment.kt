package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.forEach
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.RepeatInterval
import com.perpetio.alertapp.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private var areDataSaved: Boolean = true

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
                rgRepeatInterval.visibility = if (isChecked) View.VISIBLE else View.GONE
                enableSaving(true)
            }
            rgRepeatInterval.setOnCheckedChangeListener { radioGroup, buttonId ->
                enableSaving(true)
            }
            btnSave.setOnClickListener {
                saveSettings()
                enableSaving(false)
            }
        }
    }

    private fun enableSaving(value: Boolean) {
        binding.btnSave.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun showSettings() {
        binding.apply {
            app.storage.repeatInterval?.let { repeatInterval ->
                val intervals = RepeatInterval.values().toList()
                intervals.find { interval ->
                    interval.minutes == repeatInterval
                }?.let { interval ->
                    rgRepeatInterval.check(interval.btnId)
                }
                chkAutoUpdate.isChecked = true
            }
        }
    }

    private fun saveSettings() {
        binding.apply {
            if (chkAutoUpdate.isChecked) {
                val checkedButtonId = rgRepeatInterval.checkedRadioButtonId
                val intervals = RepeatInterval.values().toList()
                intervals.find { interval ->
                    interval.btnId == checkedButtonId
                }?.let { interval ->
                    app.storage.repeatInterval = interval.minutes
                }
            } else app.storage.repeatInterval = null
        }
    }
}