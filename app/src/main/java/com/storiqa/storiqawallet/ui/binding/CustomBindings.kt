package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.storiqa.storiqawallet.common.resolveCardBackground
import com.storiqa.storiqawallet.common.resolveCurrencyIcon

@BindingAdapter("imageResource")
fun ImageView.imageResource(res: Int) {
    setImageResource(res)
}

@BindingAdapter("imageDrawable")
fun ImageView.imageDrawable(drawable: Drawable) {
    setImageDrawable(drawable)
}

@BindingAdapter("currencyIcon")
fun ImageView.currencyIcon(currencyISO: String) {
    setImageResource(resolveCurrencyIcon(currencyISO))
}

@BindingAdapter("cardBackground")
fun ConstraintLayout.cardBackground(currencyISO: String) {
    setBackgroundResource(resolveCardBackground(currencyISO))
}