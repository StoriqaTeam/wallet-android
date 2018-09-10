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
        viewState.disableSignInButton()
        viewState.hideKeyboard()

        model.signInWithEmailAndPassword(email, password, {
            viewState.hideProgressBar()
            startNextScreen()
        }) {errors ->
            viewState.enableSignInButton()
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

    fun requestTokenFromGoogleAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().google, {
            startNextScreen()
        }, {
            viewState.showSignInError()
        })
    }

    fun requestTokenFromFacebookAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().facebook, {
            startNextScreen()
        }, {
            viewState.showSignInError()
        })
    }

    fun startNextScreen() {
        if (model.isUserFinishedQuickLaunch()) {
            viewState.startMainScreen()
        } else {
            viewState.startQuickLaunchScreen()
        }
    }

    fun onRegisterButtonClicked() {
        viewState.startRegisterScreen()
    }

    fun onForgotPasswordButtonClicked() {
        viewState.openRecoverPasswordScreen()
    }

    fun redirectIfAlternativeLoginSetted() {
        if(model.isPinCodeEntered()) {
            viewState.openPinCodeEnterSceenForLogin()
        }
    }

}