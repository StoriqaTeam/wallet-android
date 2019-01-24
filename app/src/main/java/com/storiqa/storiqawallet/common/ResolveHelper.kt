package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.R

class ResolveHelper {

    fun getCurrencyIcon(currencyISO: String): Int {
        return when (currencyISO) {
            "btc" -> R.drawable.btc_small_logo
            "eth" -> R.drawable.eth_small_logo
            "stq" -> R.drawable.stq_small_logo
            else -> throw Exception("Not found icon for $currencyISO")
        }
    }

    fun getCardBackground(currencyISO: String): Int {
        return when (currencyISO) {
            "btc" -> R.drawable.card_background_btc
            "eth" -> R.drawable.card_background_eth
            "stq" -> R.drawable.card_background_stq
            else -> throw Exception("Not found background for $currencyISO")
        }
    }

    fun getCurrencySymbol(currencyISO: String): String {
        return when (currencyISO) {
            "USD" -> "\$"
            "RUB" -> "\u20BD"
            else -> throw Exception("Not found symbol for $currencyISO")
        }
    }

    fun getSignificantDigits(currencyISO: String): Int {
        return when (currencyISO) {
            "btc" -> 8
            "eth", "stq" -> 18
            else -> throw Exception("Not found coefficient for $currencyISO")
        }
    }

}