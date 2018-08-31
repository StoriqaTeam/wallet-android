package com.storiqa.storiqawallet.login_screen

import com.storiqa.storiqawallet.network.network_responses.ErrorInfo
import org.json.JSONArray

interface LoginModel {
    fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors : List<ErrorInfo>) -> Unit)
    fun getErrors(jsonArray: JSONArray): String
    fun getStoriqaToken(userToken: String, provider: String, success: (storiqaToken: String) -> Unit, failure: () -> Unit)
}