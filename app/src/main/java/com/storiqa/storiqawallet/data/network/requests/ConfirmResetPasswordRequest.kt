package com.storiqa.storiqawallet.data.network.requests

data class ConfirmResetPasswordRequest(
        val token: String,
        val password: String)