package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency

interface ICurrencyFormatter {

    fun getFormattedAmount(amount: String, currency: Currency, isSymbolNeeded: Boolean = true): String

    fun getStringAmount(formattedAmount: String, currency: Currency): String
}