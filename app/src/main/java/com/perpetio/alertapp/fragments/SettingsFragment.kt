package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.forEach
import com.perpetio.alertapp.R
import com.perpetio.alertapp.data.RepeatInterval
import com.perpetio.alertapp.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.apply {
            val intervals = RepeatInterval.values().toList()
            rgRepeatInterval.forEach { button ->
                intervals.find { interval ->
                    interval.bntId == button.id
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
            }
        }
    }
}