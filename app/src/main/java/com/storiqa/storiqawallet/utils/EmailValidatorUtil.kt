package com.storiqa.storiqawallet.utils

import android.util.Patterns

fun isEmailValid(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}