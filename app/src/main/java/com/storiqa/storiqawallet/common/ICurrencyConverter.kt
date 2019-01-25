package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal

interface ICurrencyConverter {

    fun convertToFiat(amount: BigDecimal, currencyCrypto: Currency): BigDecimal

}