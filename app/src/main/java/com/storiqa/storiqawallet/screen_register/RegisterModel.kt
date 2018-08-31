package com.storiqa.storiqawallet.screen_register

import com.storiqa.storiqawallet.network.network_responses.ErrorInfo

interface RegisterModel {
    fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: (errors : List<ErrorInfo>?) -> Unit)
}