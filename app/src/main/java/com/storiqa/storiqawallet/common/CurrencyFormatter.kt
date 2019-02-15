package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrencyFormatter : ICurrencyFormatter {

    override fun getFormattedAmount(amount: String, currency: Currency, isSymbolNeeded: Boolean): String {
        return when {
            currency.isFiat && !isSymbolNeeded ->
                getFormattedFiat(amount)
            currency.isFiat && isSymbolNeeded ->
                currency.getSymbol() + " " + getFormattedFiat(amount)
            !currency.isFiat && !isSymbolNeeded ->
                getFormattedCrypto(amount, currency)
            !currency.isFiat && isSymbolNeeded ->
                getFormattedCrypto(amount, currency) + " " + currency.getSymbol()
            else -> throw Exception()
        }
    }

    private fun getFormattedFiat(amount: String): String {
        val floatAmount = amount.replace(",", ".").toFloat()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_DOWN
        return df.format(floatAmount)
    }

    private fun getFormattedCrypto(amount: String, currency: Currency): String {
        val decimalAmount = BigDecimal(amount)
                .movePointLeft(currency.getSignificantDigits())
                .setScale(currency.getSignificantDigits(), RoundingMode.HALF_DOWN)
        return if (decimalAmount.compareTo(BigDecimal.ZERO) == 0)
            BigDecimal.ZERO.toPlainString()
        else
            decimalAmount.stripTrailingZeros().toPlainString()
    }
}