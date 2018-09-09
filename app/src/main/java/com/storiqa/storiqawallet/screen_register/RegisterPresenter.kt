package com.storiqa.storiqawallet.screen_register

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.storiqa.storiqawallet.objects.ErrorRetriever

@InjectViewState
class RegisterPresenter : MvpPresenter<RegisterView>() {
    val model = RegisterModelImp()

    fun onSignInButtonClicked() {
        viewState.startLoginScreen()
    }

    fun onSignUpButtonClicked(firstName: String, lastName: String, email: String, password: String, repeatedPassword: String) {
        if(password != repeatedPassword) {
            viewState.showPasswordsHaveToMatchError()
            return
        } else {
            viewState.hidePasswordsHaveToMatchError()
        }

        viewState.clearErrors()
        model.registerUser(firstName, lastName, email, password, {
            viewState.showRegistrationSuccessDialog()
        }, { errors ->
            if(errors == null) {
                viewState.showRegistrationError()
            } else {
                val errorRetriever = ErrorRetriever(errors)

                if(errorRetriever.isEmailErrorExist()) {
                    viewState.setEmailError(errorRetriever.emailErrors)
                }

                if(errorRetriever.isPasswordErrorExist()) {
                    viewState.setPasswordError(errorRetriever.passwordErrors)
                }
            }

        })
    }


}