package com.storiqa.storiqawallet.network.common

data class RequestHeaders(
        val timestamp: String,
        val deviceId: String,
        val sign: String,
        val bearer: String? = null)