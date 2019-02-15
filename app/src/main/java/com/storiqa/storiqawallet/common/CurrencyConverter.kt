package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyConverter(private val rates: List<RateEntity>,
                        private val currencyFiat: Currency = Currency.USD) : ICurrencyConverter {

    override fun convertToFiat(amountCrypto: String, currencyCrypto: Currency): String {
        var coefficient = 0.0
        for (rate in rates) {
            if (rate.currencyCrypto == currencyCrypto &&
                    rate.currencyFiat == currencyFiat) {
                coefficient = rate.price
                break
            }
        }
        val amountFiat = BigDecimal(amountCrypto)
                .movePointLeft(currencyCrypto.getSignificantDigits())
                .setScale(currencyCrypto.getSignificantDigits(), RoundingMode.HALF_DOWN)
                .multiply(BigDecimal(coefficient))
                .toPlainString()

        return CurrencyFormatter().getFormattedAmount(amountFiat, currencyFiat)
    }

    override fun convertBalance(amount: String, fromCurrency: Currency, toCurrency: Currency): String {
        var rate: RateEntity? = null
        for (r in rates) {
            if ((r.currencyCrypto == fromCurrency && r.currencyFiat == toCurrency) ||
                    (r.currencyCrypto == toCurrency && r.currencyFiat == fromCurrency)) {
                rate = r
                break
            }
        }
        if (rate == null)
            throw Exception("Not found rate for currencies: $fromCurrency and $toCurrency")

        var amountConverted = BigDecimal(amount)
        if (rate.currencyCrypto == fromCurrency)
            amountConverted = amountConverted
                    .multiply(BigDecimal(rate.price))
                    .setScale(toCurrency.getSignificantDigits(), RoundingMode.HALF_DOWN)
        else
            amountConverted = amountConverted.divide(
                    BigDecimal(rate.price),
                    toCurrency.getSignificantDigits(),
                    RoundingMode.HALF_DOWN)
        return if (amountConverted.compareTo(BigDecimal.ZERO) == 0)
            BigDecimal.ZERO.toPlainString()
        else
            amountConverted.stripTrailingZeros().toPlainString()
    }
}