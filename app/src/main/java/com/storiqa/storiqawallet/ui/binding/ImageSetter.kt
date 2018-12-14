package com.storiqa.storiqawallet.ui.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView

@BindingAdapter("imageResource")
fun ImageView.imageResource(res: Int) {
    setImageResource(res)
}

@BindingAdapter("imageDrawable")
fun ImageView.imageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}