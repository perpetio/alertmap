package com.perpetio.alertapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.perpetio.alertapp.AlertApp

abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    val app = application as AlertApp
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    fun showToast(stringRes: String?) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
    }

    fun showError(message: String?) {
        showToast(message)
    }

    abstract fun getViewBinding(): B
}