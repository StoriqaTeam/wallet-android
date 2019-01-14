package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageResource")
fun ImageView.imageResource(res: Int) {
    setImageResource(res)
}

@BindingAdapter("imageDrawable")
fun ImageView.imageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}