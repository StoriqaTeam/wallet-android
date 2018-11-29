package com.storiqa.storiqawallet.network.requests

data class RegisterUserRequest(
        val email: String = "d.kruglov@storiqa.com",
        val phone: String = "78005553535",
        val password: String = "Qwerty12345",
        val firstName: String = "Dmitry",
        val lastName: String = "Kruglov",
        val deviceOs: String = "25",
        val deviceId: String = "09bbda10-2908-4c5a-bd63-9098fc6b6ffb",
        val publicKey: String = "04965c38dc327722ab231198e63c1b9c6d5a3b5b462e2e609dfa808d7022cb35f4358fedc1ea8b529026ecb2937ba97ff1d5639501b5fc1c1da8d1f4737505b595") {

    val deviceType = "android"

}