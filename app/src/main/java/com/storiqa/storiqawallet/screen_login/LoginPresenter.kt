package com.storiqa.storiqawallet.screen_login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.storiqa.storiqawallet.constants.SignInProviders
import com.storiqa.storiqawallet.objects.ErrorRetriever

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    private val model = LoginModelImp()

    fun onSignInButtonClicked(email: String, password: String) {
        viewState.showProgressBar()

        model.signInWithEmailAndPassword(email, password, {
            viewState.startMainScreen()
            viewState.hideProgressBar()
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