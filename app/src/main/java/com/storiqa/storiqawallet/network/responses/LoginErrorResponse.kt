package com.storiqa.storiqawallet.network.responses

data class LoginErrorResponse(
        val email: Array<SomeErorr>?,
        val password: Array<SomeErorr>?,
        val device: Array<SomeErorr>?)

data class SomeErorr(
        val code: String,
        val message: String)