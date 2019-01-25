package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal

class CurrencyConverter(private val rates: List<RateEntity>,
                        private val currencyFiat: Currency = Currency.USD) : ICurrencyConverter {

    override fun convertToFiat(amount: BigDecimal, currencyCrypto: Currency): BigDecimal {
        var coefficient = 0.0
        for (rate in rates) {
            if (rate.currencyCrypto == currencyCrypto &&
                    rate.currencyFiat == currencyFiat) {
                coefficient = rate.price
                break
            }
        }

        return amount.multiply(BigDecimal(coefficient))
    }

}