package com.perpetio.alertapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.perpetio.alertapp.R

abstract class BaseDialog<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B,
    protected val activity: AppCompatActivity
) : Dialog(activity, R.style.AppTheme) {
    protected lateinit var binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): B
}