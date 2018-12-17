package com.storiqa.storiqawallet.network.requests

data class RegisterUserRequest(
        val email: String = "d.kruglov@storiqa.com",
        val phone: String = "78005553535",
        val password: String = "Qwerty12345",
        val firstName: String = "Dmitry",
        val lastName: String = "Kruglov",
        val deviceOs: String = "25",
        val deviceId: String = "09bbda10-2908-4c5a-bd63-9098fc6bffff",
        val publicKey: String = "04b569b0d199212a2d111af0d5e35b0ca52b2206799766bcaf2c9ef230ad330da18bd480dc707640880e73a3abbe06d903deae95cb2a1ed7651aa36e0b0d640319") {

    val deviceType = "android"

}