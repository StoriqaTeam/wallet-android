package com.storiqa.storiqawallet.register_screen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class RegisterPresenter : MvpPresenter<RegisterView>() {
    val model = RegisterModelImp()

    fun onSignInButtonClicked() {
        viewState.startLoginScreen()
    }

    fun onShowPasswordButtonClicked() {
        viewState.changePasswordVisibility()
    }

    fun onShowRepeatedPasswordButtonClicked() {
        viewState.changeRepeatedPasswordVisibility()
    }

    fun onFieldInformationChanged(firstName: String, lastName: String, email: String, password: String, repeatedPassword: String) {
        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatedPassword.isNotEmpty()) {
            viewState.enableSignUpButton()
        } else {
            viewState.disableSignUpButton()
        }
    }


}