package com.storiqa.storiqawallet.screen_login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.storiqa.storiqawallet.constants.SignInProviders
import com.storiqa.storiqawallet.objects.ErrorRetriever

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    private val model = LoginModelImp()

    fun onTextChanged(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewState.enableSignInButton()
        } else {
            viewState.disableSignInButton()
        }
    }

    fun onSignInButtonClicked(email: String, password: String) {
        viewState.showProgressBar()
        viewState.disableSignInButton()

        model.signInWithEmailAndPassword(email, password, {
            viewState.startMainScreen()
            viewState.hideProgressBar()
            viewState.enableSignInButton()
        }) {errors ->
            viewState.hideEmailError()
            viewState.hidePasswordError()

            if (errors.isEmpty()) {
                viewState.showSignInError()
            } else {
                val errorRetriever = ErrorRetriever(errors)

                if(errorRetriever.isEmailErrorExist()) {
                    viewState.setEmailError(errorRetriever.emailErrors)
                }

                if(errorRetriever.isPasswordErrorExist()) {
                    viewState.setPasswordError(errorRetriever.passwordErrors)
                }
            }
            viewState.hideProgressBar()
            viewState.enableSignInButton()
        }
    }

    fun onGoogleLoginClicked() {
        viewState.startGoogleSignInProcess()
    }

    fun onFacebookButtonClciked() {
        viewState.startFacebookSignInProcess()
    }

    fun requestTokenFromGoogleAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().google, {
            viewState.startMainScreen()
        }, {
            viewState.showSignInError()
        })
    }

    fun requestTokenFromFacebookAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().facebook, {
            viewState.startMainScreen()
        }, {
            viewState.showSignInError()
        })
    }

    fun onRegisterButtonClicked() {
        viewState.startRegisterScreen()
    }

    fun onForgotPasswordButtonClicked() {
        viewState.openRecoverPasswordScreen()
    }

}