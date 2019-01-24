package com.storiqa.storiqawallet.common

import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyFormatter : ICurrencyFormatter {

    override fun getFormattedDecimal(amount: String, currency: String): BigDecimal {
        return BigDecimal(amount).movePointLeft(getSignificantDigits(currency))
    }

    override fun getFormattedString(amount: BigDecimal, currency: String): String {
        val formattedAmount = amount
                .setScale(getSignificantDigits(currency), RoundingMode.HALF_UP)
        return if (formattedAmount.compareTo(BigDecimal.ZERO) == 0)
            BigDecimal.ZERO.toPlainString()
        else
            formattedAmount.stripTrailingZeros().toPlainString()
    }

    private fun getSignificantDigits(currencyISO: String): Int {
        return when (currencyISO) {
            "btc" -> 8
            "eth", "stq" -> 18
            else -> 2
        }
    }

}