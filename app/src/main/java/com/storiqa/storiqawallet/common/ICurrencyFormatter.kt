package com.storiqa.storiqawallet.common

import java.math.BigDecimal

interface ICurrencyFormatter {

    fun getFormattedDecimal(amount: String, currency: String): BigDecimal

    fun getFormattedString(amount: BigDecimal, currency: String): String

}