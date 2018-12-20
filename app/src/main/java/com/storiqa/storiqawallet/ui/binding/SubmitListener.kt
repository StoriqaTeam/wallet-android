package com.storiqa.storiqawallet.ui.binding

import android.databinding.BindingAdapter
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText


/*
@BindingAdapter("onEditorEnterAction")
fun EditText.onEditorEnterAction(f: () -> Unit) {

    setOnEditorActionListener { textView, actionId, event ->

        val imeAction = when (actionId) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_GO -> true
            else -> false
        }

        val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
                && event.action == KeyEvent.ACTION_DOWN

        if (imeAction or keydownEvent)
            true.also {
                textView.clearFocus()
                f()
            }
        else false
    }
}*/
@BindingAdapter("onEditorEnterAction")
fun EditText.onEditorEnterAction(callback: SubmitButtonCallback) {

    setOnEditorActionListener { textView, actionId, event ->

        val imeAction = when (actionId) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_GO -> true
            else -> false
        }

        val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
                && event.action == KeyEvent.ACTION_DOWN

        if (imeAction or keydownEvent)
            true.also {
                //removeFocus(textView)
                //clearFocus()
                callback.onSubmitButtonClicked()
            }
        else false
    }
}

interface SubmitButtonCallback {
    fun onSubmitButtonClicked()
}