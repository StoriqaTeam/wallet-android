package com.storiqa.storiqawallet.ui.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference

class SpacesWatcher(editText: EditText) : TextWatcher {

    private var refTextView: WeakReference<EditText> = WeakReference(editText)

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val filteredText = filter(s)
        if (filteredText != s.toString()) {
            var selection = getTextView()?.selectionEnd ?: return
            getTextView()?.setText(filteredText)

            if (selection - 1 <= filteredText.length)
                selection--
            else
                selection = filteredText.length

            getTextView()?.setSelection(selection)
        }
    }

    private fun getTextView(): EditText? {
        return refTextView.get()
    }

    private fun filter(source: CharSequence?): String {
        if (source == null)
            return ""

        var result = source.toString()

        while (result.contains("  "))
            result = result.replace("  ", " ")

        return result.removePrefix(" ")
    }
}