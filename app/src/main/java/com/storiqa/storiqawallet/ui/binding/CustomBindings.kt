package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.storiqa.storiqawallet.common.resolveCardBackground
import com.storiqa.storiqawallet.common.resolveCurrencyIcon
import com.storiqa.storiqawallet.common.resolveCurrencySymbol

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

@BindingAdapter("equalBalance", "currency")
fun TextView.equalBalance(balance: Double, currency: String) {
    text = resolveCurrencySymbol(currency) + balance
}