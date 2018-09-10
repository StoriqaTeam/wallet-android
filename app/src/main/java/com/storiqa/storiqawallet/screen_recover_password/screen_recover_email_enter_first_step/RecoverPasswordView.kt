package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import com.arellomobile.mvp.MvpView

interface RecoverPasswordView : MvpView {
    fun onNewPasswordEmailSent(email: String)
    fun goBack()
    fun showGeneralError()
}