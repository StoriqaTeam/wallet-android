package com.storiqa.storiqawallet.network.requests

data class UpdateUserRequest(
        val phone: String = "78005555555",
        val firstName: String = "Dmitry2",
        val lastName: String = "Kruglov2") {
}