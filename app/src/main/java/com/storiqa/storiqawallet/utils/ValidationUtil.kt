package com.storiqa.storiqawallet.utils

import android.util.Patterns
import com.storiqa.storiqawallet.BuildConfig
import com.storiqa.storiqawallet.data.model.Currency
import java.util.regex.Pattern

fun isEmailValid(email: String): Boolean {
    return email.isNotEmpty() && email.indexOf("@") < 30 &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isUserNameValid(name: String): Boolean {
    val expression = "^(?=.{1,30}\$)[a-zA-Zа-яА-я0-9]+(('|-| )[a-zA-Zа-яА-я0-9]+)*\$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    return pattern.matcher(name).matches()
}

fun isAddressValid(address: String, currency: Currency): Boolean {
    return when (currency) {
        Currency.ETH, Currency.STQ -> address.length == 42 && address.startsWith("0x")
        Currency.BTC -> {
            val data = address.decodeBase58()
            if (data.size != 25)
                return false
            val versionByte = data[0].toInt()
            return (BuildConfig.DEBUG && (versionByte == 111 || versionByte == 196)) ||
                    (!BuildConfig.DEBUG && (versionByte == 0 || versionByte == 5))
        }
        else -> throw Exception("Not found validator for currency $currency")
    }
}