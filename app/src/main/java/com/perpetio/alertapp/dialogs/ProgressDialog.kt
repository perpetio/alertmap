package com.perpetio.alertapp.dialogs

import androidx.appcompat.app.AppCompatActivity
import com.perpetio.alertapp.databinding.DialogProgressBinding

class ProgressDialog(
    activity: AppCompatActivity
) : BaseDialog<DialogProgressBinding>(
    DialogProgressBinding::inflate,
    activity
) {
    override fun onBackPressed() {
        return
    }

    override fun getViewBinding(): DialogProgressBinding {
        return DialogProgressBinding.inflate(layoutInflater)
    }
}