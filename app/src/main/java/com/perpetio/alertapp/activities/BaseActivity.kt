package com.perpetio.alertapp.activities

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.perpetio.alertapp.R

open class BaseActivity: AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, null, null, true, false)
            progressDialog?.setContentView(R.layout.dialog_progress)
            progressDialog?.getWindow()?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            progressDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog?.setCancelable(false)
        }
        if (progressDialog != null && !progressDialog?.isShowing()!!) {
            progressDialog?.show()
        }
    }

    fun hideProgress() {
        if (progressDialog != null) {
            progressDialog?.hide()
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    protected open fun showToast(stringRes: Int) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
    }

    protected open fun showToast(stringRes: String?) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
    }

    protected open fun showError(message: String?) {
        hideProgress()
        showToast(message)
    }
}