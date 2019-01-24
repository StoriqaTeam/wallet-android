package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency
import java.math.BigDecimal

interface ICurrencyFormatter {

    fun getFormattedDecimal(amount: String, currency: Currency): BigDecimal

    fun getBalanceWithoutSymbol(amount: BigDecimal, currency: Currency): String

    fun getBalanceWithFiatSymbol(amount: BigDecimal, currency: Currency): String

}