package com.storiqa.storiqawallet.utils

import android.util.Patterns
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