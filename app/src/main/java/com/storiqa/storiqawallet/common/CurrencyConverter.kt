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
                .setScale(currencyCrypto.getSignificantDigits(), RoundingMode.HALF_UP)
                .multiply(BigDecimal(coefficient))
                .toPlainString()

        return CurrencyFormatter().getFormattedAmount(amountFiat, currencyFiat)
    }

}