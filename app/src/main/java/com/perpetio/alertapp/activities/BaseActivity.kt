package com.perpetio.alertapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.perpetio.alertapp.dialogs.ProgressDialog

abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {
    protected lateinit var binding: B
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
    }

    fun showProgress() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun showToast(stringRes: String?) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
    }

    fun showError(message: String?) {
        hideProgress()
        showToast(message)
    }

    abstract fun getViewBinding(): B
}