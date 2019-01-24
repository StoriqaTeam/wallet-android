package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.storiqa.storiqawallet.common.ResolveHelper

@BindingAdapter("imageResource")
fun ImageView.imageResource(res: Int) {
    setImageResource(res)
}

@BindingAdapter("imageDrawable")
fun ImageView.imageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}

@BindingAdapter("cardBackground")
fun ConstraintLayout.cardBackground(currencyISO: String) {
    setBackgroundResource(ResolveHelper().getCardBackground(currencyISO))
}