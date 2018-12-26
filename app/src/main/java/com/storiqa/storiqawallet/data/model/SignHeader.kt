package com.storiqa.storiqawallet.data.model

data class SignHeader(
        val deviceId: String,
        val timestamp: String,
        val signature: String,
        val pubKeyHex: String)