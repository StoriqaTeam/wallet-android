package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import com.arellomobile.mvp.MvpView

interface RecoverPasswordView : MvpView {
    fun openNewPasswordEnterScreen(email: String)
    fun goBack()
}