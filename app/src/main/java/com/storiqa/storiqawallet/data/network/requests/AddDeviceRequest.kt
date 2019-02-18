package com.storiqa.storiqawallet.data.network.requests

data class AddDeviceRequest(
        val userId: Long,
        val deviceOs: String,
        val deviceId: String,
        val publicKey: String)