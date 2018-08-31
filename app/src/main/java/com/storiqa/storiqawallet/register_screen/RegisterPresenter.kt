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

    fun onFieldInformationChanged(firstName: String, lastName: String, email: String, password: String, repeatedPassword: String, isLicenseAgreed: Boolean) {
        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatedPassword.isNotEmpty() && isLicenseAgreed) {
            viewState.enableSignUpButton()
        } else {
            viewState.disableSignUpButton()
        }
    }

    fun onSignUpButtonClicked(firstName: String, lastName: String, email: String, password: String, repeatedPassword: String) {
        if(password != repeatedPassword) {
            viewState.showPasswordsHaveToMatchError()
            return
        } else {
            viewState.hidePasswordsHaveToMatchError()
        }

        model.registerUser(firstName, lastName, email, password, {
            viewState.showRegistrationSuccessDialog()
        }, {
            viewState.showRegistrationError()
        })
    }


}