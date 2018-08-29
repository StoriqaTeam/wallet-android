package com.storiqa.storiqawallet.login_screen

interface LoginModel {
    fun verifyEmail(email: String, onVerified: (result: Boolean) -> Unit, failure: () -> Unit)
}