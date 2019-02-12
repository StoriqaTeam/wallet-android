package com.storiqa.storiqawallet.data.network.responses

data class UserInfoResponse(
        val id: Long,
        val email: String,
        val phone: String?,
        val firstName: String,
        val lastName: String)