package com.storiqa.storiqawallet.screen_register

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.storiqa.storiqawallet.objects.ErrorRetriever
import com.storiqa.storiqawallet.screen_login.LoginModelImp

@InjectViewState
class RegisterPresenter : MvpPresenter<RegisterView>() {
    val model = RegisterModelImp()

    fun onSignInButtonClicked() {
        viewState.startLoginScreen()
    }

    fun onSignUpButtonClicked(firstName: String, lastName: String, email: String, password: String, repeatedPassword: String) {
        if (password != repeatedPassword) {
            viewState.showPasswordsHaveToMatchError()
            return
        } else {
            viewState.hidePasswordsHaveToMatchError()
        }

        viewState.clearErrors()
        model.registerUser(firstName, lastName, email, password, {
            viewState.showRegistrationSuccessDialog()
        }, { errors ->
            if (errors == null) {
                viewState.showRegistrationError()
            } else {
                val errorRetriever = ErrorRetriever(errors)

                if (errorRetriever.isEmailErrorExist()) {
                    viewState.setEmailError(errorRetriever.emailErrors)
                }

                if (errorRetriever.isPasswordErrorExist()) {
                    viewState.setPasswordError(errorRetriever.passwordErrors)
                }
            }

        })
    }

    fun requestTokenFromGoogleAccount(googleToken: String) {
        LoginModelImp().getStoriqaToken(googleToken, "GOOGLE", {
            viewState.startLoginScreen()
        }, {
            viewState.showGoogleSignInError()
        })
    }

    fun requestTokenFromFacebookAccount(facebookToken: String) {
        LoginModelImp().getStoriqaToken(facebookToken, "FACEBOOK", {
            viewState.startLoginScreen()
        }, {
            viewState.showFacebookSignInError()
        })
    }


}