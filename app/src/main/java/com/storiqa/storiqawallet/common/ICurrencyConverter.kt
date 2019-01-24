package com.storiqa.storiqawallet.common

import java.math.BigDecimal

interface ICurrencyConverter {

    fun convertToFiat(amount: BigDecimal, currencyCrypto: String): BigDecimal

}