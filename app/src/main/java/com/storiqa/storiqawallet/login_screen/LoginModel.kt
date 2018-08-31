package com.storiqa.storiqawallet.login_screen

import com.storiqa.storiqawallet.network.network_responses.ErrorInfo

interface LoginModel {
    fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors : List<ErrorInfo>) -> Unit)
    fun getStoriqaToken(userToken: String, provider: String, success: (storiqaToken: String) -> Unit, failure: () -> Unit)
}