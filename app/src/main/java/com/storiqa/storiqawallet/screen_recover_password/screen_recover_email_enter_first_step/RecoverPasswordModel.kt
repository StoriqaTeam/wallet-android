package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import com.storiqa.storiqawallet.network.network_responses.ResetPasswordResponse


interface RecoverPasswordModel {
    fun resetPassword(email: String, sucess: (resetPasswordResponse: ResetPasswordResponse) -> Unit, failure: () -> Unit)
}