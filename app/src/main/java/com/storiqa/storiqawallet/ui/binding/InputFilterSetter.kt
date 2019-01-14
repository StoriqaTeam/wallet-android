package com.storiqa.storiqawallet.ui.binding

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("addTextFilter")
fun EditText.addTextFilter(isEnabled: Boolean) {
    if (isEnabled) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredText = filter(s)
                if (filteredText != s.toString()) {
                    var selection = selectionEnd
                    setText(filteredText)

                    if (selection - 1 <= filteredText.length)
                        selection--
                    else
                        selection = filteredText.length

                    setSelection(selection)
                }
            }
        })
    }
}

private fun filter(source: CharSequence?): String {
    if (source == null)
        return ""

    var result = source.toString()

    while (result.contains("  "))
        result = result.replace("  ", " ")

    return result.removePrefix(" ")
}