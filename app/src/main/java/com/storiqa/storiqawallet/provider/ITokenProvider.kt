package com.storiqa.storiqawallet.provider

interface ITokenProvider {

    val accessToken: String

    fun refreshToken(): String?

    fun revokeToken(errorHandler: (Exception) -> Unit)

}