package com.perpetio.alertapp.dialogs

import android.content.Context
import android.os.Bundle
import com.perpetio.alertapp.databinding.DialogSelectTerritotyBinding

class SelectTerritoryDialog(
    context: Context
) : BaseDialog<DialogSelectTerritotyBinding>(context) {

    override fun getViewBinding(): DialogSelectTerritotyBinding {
        return DialogSelectTerritotyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            dismiss()
        }
    }
}