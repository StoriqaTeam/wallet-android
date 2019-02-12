package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("imageResource")
fun ImageView.imageResource(res: Int) {
    setImageResource(res)
}

@BindingAdapter("imageDrawable")
fun ImageView.imageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}

@BindingAdapter("backgroundResource")
fun ConstraintLayout.backgroundResource(res: Int) {
    setBackgroundResource(res)
}

@BindingAdapter("errorText")
fun TextInputLayout.errorText(text: String?) {
    error = if (text == null || text.isEmpty()) null else text
}