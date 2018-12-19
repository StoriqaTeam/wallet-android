package com.storiqa.storiqawallet.ui.binding

import android.databinding.BindingAdapter
import android.widget.EditText


@BindingAdapter("bind:onFocusLost")
fun EditText.onFocusLost(f: () -> Unit) {

    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus)
            f()
    }

}