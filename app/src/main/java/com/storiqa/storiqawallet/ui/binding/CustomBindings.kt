package com.storiqa.storiqawallet.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.storiqa.storiqawallet.common.resolveCardBackground
import com.storiqa.storiqawallet.common.resolveCoefficient
import com.storiqa.storiqawallet.common.resolveCurrencyIcon
import com.storiqa.storiqawallet.common.resolveCurrencySymbol
import java.math.BigDecimal
import java.math.RoundingMode

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

@BindingAdapter("equalBalance", "currencyFiat", "currencyCrypto", "coefficient")
fun TextView.equalBalance(balance: String, currencyFiat: String, currencyCrypto: String, coefficient: Double) {
    var ccc = BigDecimal(balance)
    var ccc0 = resolveCoefficient(currencyCrypto)
    var ccc1 = ccc.divide(ccc0)
    var ccc2 = ccc1.multiply(BigDecimal(coefficient))
    var ccc3 = ccc2.stripTrailingZeros()
    if (ccc.compareTo(BigDecimal.ZERO) == 0) {
        ccc = BigDecimal.ZERO
    } else {
        ccc = ccc.stripTrailingZeros()
    }
    text = resolveCurrencySymbol(currencyFiat) + ' ' + ccc3.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() //TODO refactor
}

@BindingAdapter("balance", "currency")
fun TextView.balance(balance: String, currency: String) {
    var ccc = BigDecimal(balance).divide(resolveCoefficient(currency)).stripTrailingZeros()
    if (ccc.compareTo(BigDecimal.ZERO) == 0) {
        ccc = BigDecimal.ZERO
    } else {
        ccc = ccc.stripTrailingZeros()
    }
    text = ccc.setScale(18, RoundingMode.DOWN).stripTrailingZeros().toPlainString() + ' ' + currency.toUpperCase() //TODO refactor
}