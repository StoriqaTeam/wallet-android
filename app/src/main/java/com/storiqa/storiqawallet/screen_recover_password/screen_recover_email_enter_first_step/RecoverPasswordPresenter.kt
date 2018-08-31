package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class RecoverPasswordPresenter : MvpPresenter<RecoverPasswordView>() {
    val model = RecoverPasswordModelImp()

    fun onEmailTextChanged(email: String) {
        if(email.isEmpty()) {
            viewState.disableResetPasswordButton()
        } else {
            viewState.enableResetPasswordButton()
        }
    }

    fun onResetPasswordButtonClicked(email: String) {
        viewState.openNewPasswordEnterScreen(email)
    }

    fun onBackButtonPressed() {
        viewState.goBack()
    }

}