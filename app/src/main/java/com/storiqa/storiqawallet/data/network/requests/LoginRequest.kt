package com.storiqa.storiqawallet.data.network.requests

data class LoginRequest(
        val email: String = "d.kruglov@storiqa.com",
        val password: String = "Qwerty12345",
        val deviceOs: String = "25",
        val deviceId: String = "09bbda10-2908-4c5a-bd63-9098fc6bffff") {

    val deviceType = "android"

}