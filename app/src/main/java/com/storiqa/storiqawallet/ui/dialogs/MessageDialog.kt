package com.storiqa.storiqawallet.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.network.errors.ErrorData


class MessageDialog : DialogFragment() {

    private val argumentTitle = "argument_title"
    private val argumentMessage = "argument_message"
    private val argumentIcon = "argument_icon"

    fun getInstance(errorData: ErrorData): MessageDialog {
        val messageDialog = MessageDialog()
        val bundle = Bundle()
        bundle.putInt(argumentTitle, errorData.title)
        bundle.putInt(argumentMessage, errorData.description!!)
        bundle.putInt(argumentIcon, errorData.icon!!)
        messageDialog.arguments = bundle
        return messageDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater

        builder.setView(inflater.inflate(R.layout.dialog_message, null))
        return builder.create()
    }
}