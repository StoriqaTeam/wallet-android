package com.storiqa.storiqawallet.network.responses

data class UserInfoResponse(
        val id: Int,
        val email: String,
        val phone: String,
        val firstName: String,
        val lastName: String)