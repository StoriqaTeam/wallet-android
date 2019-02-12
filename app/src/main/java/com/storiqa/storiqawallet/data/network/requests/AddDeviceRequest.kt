package com.storiqa.storiqawallet.data.network.requests

data class AddDeviceRequest(
        val userId: Int,
        val deviceOs: String,
        val deviceId: String,
        val publicKey: String)