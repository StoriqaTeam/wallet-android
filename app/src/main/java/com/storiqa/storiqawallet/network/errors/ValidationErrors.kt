package com.storiqa.storiqawallet.network.errors

data class ValidationError(
        val code: String,
        val message: String,
        val params: HashMap<String, String>)

data class ErrorParams(
        val value: String)