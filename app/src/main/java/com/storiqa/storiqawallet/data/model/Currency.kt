package com.storiqa.storiqawallet.data.model

import com.google.gson.annotations.SerializedName
import com.storiqa.storiqawallet.R

enum class Currency(val currencyISO: String) {

    @SerializedName(value = "ETH", alternate = ["eth"])
    ETH("ETH"),

    @SerializedName(value = "BTC", alternate = ["btc"])
    BTC("BTC"),

    @SerializedName(value = "STQ", alternate = ["stq"])
    STQ("STQ"),

    @SerializedName(value = "RUB", alternate = ["rub"])
    RUB("RUB"),

    @SerializedName(value = "USD", alternate = ["usd"])
    USD("USD"),

    @SerializedName(value = "EUR", alternate = ["eur"])
    EUR("EUR");

    val isFiat: Boolean
        get() = when (this) {
            ETH, BTC, STQ -> false
            RUB, USD, EUR -> true
        }

    fun getSymbol(): String {
        return when (this) {
            ETH -> "ETH"
            BTC -> "BTC"
            STQ -> "STQ"
            RUB -> "\u20BD"
            USD -> "$"
            EUR -> "€"
        }
    }

    fun getSignificantDigits(): Int {
        return when (this) {
            BTC -> 8
            ETH, STQ -> 18
            else -> 2
        }
    }

    fun getCurrencyIcon(): Int {
        return when (this) {
            BTC -> R.drawable.ic_btc
            ETH -> R.drawable.ic_eth
            STQ -> R.drawable.ic_stq
            else -> throw Exception("Not found icon for $currencyISO")
        }
    }

    fun getCardBackground(): Int {
        return when (this) {
            BTC -> R.drawable.card_background_btc
            ETH -> R.drawable.card_background_eth
            STQ -> R.drawable.card_background_stq
            else -> throw Exception("Not found background for $currencyISO")
        }
    }

}