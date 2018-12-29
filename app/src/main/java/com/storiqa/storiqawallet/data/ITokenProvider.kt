package com.storiqa.storiqawallet.data

interface ITokenProvider {

    fun isExpired(token: String): Boolean

    fun refreshToken(f: (String) -> Unit, errorHandler: (Exception) -> Unit)

    fun revokeToken(errorHandler: (Exception) -> Unit)

}