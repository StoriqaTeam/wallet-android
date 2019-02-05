package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CurrencyFormatter(private val currencyFiat: Currency = Currency.USD) : ICurrencyFormatter {

    override fun getFormattedDecimal(amount: String, currency: Currency): BigDecimal {
        return BigDecimal(amount).movePointLeft(currency.getSignificantDigits())
    }

    override fun getBalanceWithoutSymbol(amount: BigDecimal, currency: Currency): String {
        val formattedAmount = amount
                .setScale(currency.getSignificantDigits(), RoundingMode.HALF_UP)
        return if (formattedAmount.compareTo(BigDecimal.ZERO) == 0)
            BigDecimal.ZERO.toPlainString()
        else
            formattedAmount.stripTrailingZeros().toPlainString()
    }

    override fun getBalanceWithFiatSymbol(amount: BigDecimal, currency: Currency): String {
        return currencyFiat.getSymbol() + " " + getBalanceWithoutSymbol(amount, currency)
    }

    fun getAmountWithSymbol(amount: String, currency: Currency): String {
        return if (currency.isFiat)
            currency.getSymbol() + " " + getFormattedFiat(amount)
        else
            getFormattedCrypto(amount, currency) + " " + currency.getSymbol()
    }

    private fun getFormattedFiat(amount: String): String {
        val floatAmount = amount.replace(",", ".").toFloat()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(floatAmount)
    }

    private fun getFormattedCrypto(amount: String, currency: Currency): String {
        val decimalAmount = BigDecimal(amount)
                .movePointLeft(currency.getSignificantDigits())
                .setScale(currency.getSignificantDigits(), RoundingMode.HALF_UP)
        return if (decimalAmount.compareTo(BigDecimal.ZERO) == 0)
            BigDecimal.ZERO.toPlainString()
        else
            decimalAmount.stripTrailingZeros().toPlainString()
    }
}