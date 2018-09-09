package com.storiqa.storiqawallet.screen_login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showPassword()
    fun hidePassword()
    fun hideEmailError()
    fun hidePasswordError()
    fun showGeneralError()
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
    fun startSetupLoginScreen()
    fun startQuickLaunchScreen()
    fun disableSignInButton()
    fun enableSignInButton()
    fun hideKeyboard()
    fun openPinCodeEnterSceenForLogin()
}