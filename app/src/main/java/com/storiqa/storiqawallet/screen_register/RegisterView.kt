package com.storiqa.storiqawallet.screen_register

import com.arellomobile.mvp.MvpView

interface RegisterView : MvpView {
    fun startLoginScreen()
    fun showPasswordsHaveToMatchError()
    fun hidePasswordsHaveToMatchError()
    fun showRegistrationSuccessDialog()
    fun showRegistrationError()
    fun setEmailError(emailError: String)
    fun setPasswordError(passwordError: String)
    fun clearErrors()
    fun startGoogleSignInProcess()
    fun startFacebookSignInProcess()
}