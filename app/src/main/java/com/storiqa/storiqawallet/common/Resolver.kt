package com.storiqa.storiqawallet.common

import com.storiqa.storiqawallet.R

fun resolveCurrencyIcon(currencyISO: String): Int {
    return when (currencyISO) {
        "btc" -> R.drawable.btc_small_logo
        "eth" -> R.drawable.eth_small_logo
        "stq" -> R.drawable.stq_small_logo
        else -> throw Exception("Not found icon for $currencyISO")
    }
}

fun resolveCardBackground(currencyISO: String): Int {
    return when (currencyISO) {
        "btc" -> R.drawable.card_background_btc
        "eth" -> R.drawable.card_background_eth
        "stq" -> R.drawable.card_background_stq
        else -> throw Exception("Not found background for $currencyISO")
    }
}

fun resolveCurrencySymbol(currencyISO: String): String {
    return when (currencyISO) {
        "USD" -> "\$"
        "RUB" -> "\u20BD"
        else -> throw Exception("Not found symbol for $currencyISO")
    }
}