package com.storiqa.storiqawallet.data.network.requests

data class RegisterUserRequest(
        val email: String = "d.kruglov@storiqa.com",
        val phone: String = "78005553535",
        val password: String = "Qwerty12345",
        val firstName: String = "Dmitry",
        val lastName: String = "Kruglov",
        val deviceOs: String = "25",
        val deviceId: String = "09bbda10-2908-4c5a-bd63-9098fc6bffff",
        val publicKey: String = "0482b233ccc3c44657212e16b0b0e699494ea082030f403034ce16f06630fde5886f89f95ef01ad4e3cad2a95b7d2aee08b0a37a400bb989ff9002cdf7efd7cd5a") {

    val deviceType = "android"

}