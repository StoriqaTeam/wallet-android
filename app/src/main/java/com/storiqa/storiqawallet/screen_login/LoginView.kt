package com.storiqa.storiqawallet.screen_login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showPassword()
    fun hidePassword()
    fun hideEmailError()
    fun hidePasswordError()
    fun showGeneralError()
    fun enableSignInButton()
    fun disableSignInButton()
    fun startMainScreen()
    fun setEmailError(error: String)
    fun setPasswordError(error: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun startGoogleSignInProcess()
    fun startFacebookSignInProcess()
    fun startRegisterScreen()
    fun showSignInError()
    fun openRecoverPasswordScreen()
}