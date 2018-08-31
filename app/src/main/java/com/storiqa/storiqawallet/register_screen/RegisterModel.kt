package com.storiqa.storiqawallet.register_screen

import com.storiqa.storiqawallet.network.network_responses.ErrorInfo

interface RegisterModel {
    fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: (errors : List<ErrorInfo>?) -> Unit)
}