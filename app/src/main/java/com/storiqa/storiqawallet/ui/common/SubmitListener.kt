package com.storiqa.storiqawallet.ui.common

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView

fun onSubmitButtonClicked(textView: TextView, actionId: Int, event: KeyEvent?,
                          onSubmitButtonClicked: () -> Unit): Boolean {
    val imeAction = when (actionId) {
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_SEND,
        EditorInfo.IME_ACTION_GO -> true
        else -> false
    }

    val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
            && event.action == KeyEvent.ACTION_DOWN

    if (imeAction or keydownEvent)
        return true.also {
            //removeFocus(textView)
            textView.clearFocus()
            onSubmitButtonClicked()
        }
    else
        return false
}