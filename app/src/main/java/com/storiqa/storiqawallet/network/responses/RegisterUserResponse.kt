package com.storiqa.storiqawallet.network.responses

data class RegisterUserResponse(
        val id: String,
        val email: String,
        val phone: String,
        val firstName: String,
        val lastName: String)