package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.db.entity.RateEntity
import java.math.BigDecimal

class CurrencyConverter(private val rates: List<RateEntity>,
                        private val currencyFiat: String = "USD") : ICurrencyConverter {

    override fun convertToFiat(amount: BigDecimal, currencyCrypto: String): BigDecimal {
        var coefficient = 0.0
        for (rate in rates) {
            if (rate.currencyCrypto.equals(currencyCrypto, true) &&
                    rate.currencyFiat.equals(currencyFiat, true)) {
                coefficient = rate.price
                break
            }
        }

        return amount.multiply(BigDecimal(coefficient))
    }

}