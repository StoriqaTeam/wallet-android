package com.storiqa.storiqawallet.utils

import android.util.Patterns

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}