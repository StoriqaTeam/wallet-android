package com.storiqa.storiqawallet.register_screen

interface RegisterModel {
    fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: () -> Unit)
}