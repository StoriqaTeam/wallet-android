package com.storiqa.storiqawallet.screen_login

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.constants.SignInProviders
import com.storiqa.storiqawallet.objects.ErrorRetriever

class LoginViewModel(val view : LoginView) : ViewModel() {
    private val model = LoginModelImp()

    val isProgressBarVisible = ObservableField<Boolean>(false)
    val isSignInButtonEnabled = ObservableField<Boolean>(false)
    val emailError = ObservableField<String>("")
    val passwordError = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")

    fun onSignInButtonClicked() {
        val email = view.getEmail()
        val password = view.getPassword()

        isProgressBarVisible.set(true)
        isSignInButtonEnabled.set(false)
        view.hideKeyboard()

        model.signInWithEmailAndPassword(email, password, {
            isProgressBarVisible.set(false)
            startNextScreen()
        }) {errors ->
            isSignInButtonEnabled.set(true)
            emailError.set("")
            passwordError.set("")

            if (errors.isEmpty()) {
                view.showSignInError()
            } else {
                val errorRetriever = ErrorRetriever(errors)

                if(errorRetriever.isEmailErrorExist()) {
                    emailError.set(errorRetriever.emailErrors)
                }

                if(errorRetriever.isPasswordErrorExist()) {
                    passwordError.set(errorRetriever.passwordErrors)
                }
            }
            isProgressBarVisible.set(false)
        }
    }

    fun startNextScreen() {
        if (model.isUserFinishedQuickLaunch()) {
            view.startMainScreen()
        } else {
            view.startQuickLaunchScreen()
        }
    }

    fun onRegisterButtonClicked() {
        view.startRegisterScreen()
    }

    fun onForgotPasswordButtonClicked() {
        view.openRecoverPasswordScreen()
    }

    fun redirectIfAlternativeLoginSetted() {
        if(model.isPinCodeEntered()) {
            view.openPinCodeEnterSceenForLogin()
        }
    }

    fun requestTokenFromGoogleAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().google, {
            startNextScreen()
        }, {
            view.showSignInError()
        })
    }

    fun requestTokenFromFacebookAccount(userToken: String) {
        model.getStoriqaToken(userToken, SignInProviders().facebook, {
            startNextScreen()
        }, {
            view.showSignInError()
        })
    }

    fun updateFields() {
        email.set(view.getEmail())
        password.set(view.getPassword())

    }
}