package com.storiqa.storiqawallet.network.errors

data class ValidationError(
        val code: ErrorCode,
        val message: String,
        val params: HashMap<String, String>?)