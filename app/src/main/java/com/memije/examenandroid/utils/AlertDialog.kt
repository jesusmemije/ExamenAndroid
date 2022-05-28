package com.memije.examenandroid.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.memije.examenandroid.R

class AlertDialog {

    private var dialog: Dialog? = null

    fun showDialog(activity: Activity?, msg: String?) {
        dialog = Dialog(activity!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.alert_dialog_layout)
        val text = dialog!!.findViewById<View>(R.id.text_dialog) as TextView
        text.text = msg
        val dialogButton: Button = dialog!!.findViewById<View>(R.id.btn_dialog) as Button
        dialogButton.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    fun hideDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

}