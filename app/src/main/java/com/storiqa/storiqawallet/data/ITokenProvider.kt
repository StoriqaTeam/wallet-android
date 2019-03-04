package com.storiqa.storiqawallet.data

interface ITokenProvider {

    fun getAccessToken(): String

    fun refreshToken(): String?

    fun revokeToken(errorHandler: (Exception) -> Unit)

}