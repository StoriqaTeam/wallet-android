package com.storiqa.storiqawallet.login_screen

import com.storiqa.storiqawallet.network.network_responses.GetTokenError

interface LoginModel {
    fun verifyEmail(email: String, onVerified: (result: Boolean) -> Unit, failure: () -> Unit)
    fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors : List<GetTokenError>) -> Unit)
}