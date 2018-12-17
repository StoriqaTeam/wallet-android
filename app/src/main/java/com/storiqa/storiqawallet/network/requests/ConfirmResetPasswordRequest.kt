package com.storiqa.storiqawallet.network.requests

data class ConfirmResetPasswordRequest(
        val token: String,
        val password: String)