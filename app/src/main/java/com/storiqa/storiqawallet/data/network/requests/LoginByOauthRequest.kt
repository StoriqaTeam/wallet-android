package com.storiqa.storiqawallet.data.network.requests

data class LoginByOauthRequest(
        val oauthToken: String,
        val oauthProvider: String,
        val deviceOs: String,
        val deviceId: String) {
    val deviceType = "android"
}