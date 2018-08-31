package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

interface RecoverPasswordModel {
    fun resetEmail(email: String, fuccess: () -> Unit, failure: () -> Unit)
}