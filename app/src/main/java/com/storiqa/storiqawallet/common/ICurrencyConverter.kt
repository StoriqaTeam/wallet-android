package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.data.model.Currency

interface ICurrencyConverter {

    fun convertToFiat(amountCrypto: String, currencyCrypto: Currency): String

    fun convertBalance(amount: String, fromCurrency: Currency, toCurrency: Currency): String

}