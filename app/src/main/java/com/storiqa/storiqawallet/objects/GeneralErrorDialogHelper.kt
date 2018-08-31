package com.storiqa.storiqawallet.objects

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.storiqa.storiqawallet.R

class GeneralErrorDialogHelper(private val context: Context) {

    fun show(tryAgainButtonClicked: () -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_sign_in_failed_dialog, null, false)
        val dialog = AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create()

        view.findViewById<Button>(R.id.btnTryAgain).setOnClickListener {
            dialog.dismiss()
            tryAgainButtonClicked()
        }
        view.findViewById<View>(R.id.btnClose).setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

}