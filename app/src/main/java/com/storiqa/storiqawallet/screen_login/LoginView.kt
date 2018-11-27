package com.storiqa.storiqawallet.screen_login

interface LoginView {
    fun showPassword()
    fun hidePassword()
    fun showGeneralError()
    fun startMainScreen()
    fun showProgressBar()
    fun hideProgressBar()
    fun startRegisterScreen()
    fun showSignInError()
    fun openRecoverPasswordScreen()
    fun startQuickLaunchScreen()
    fun disableSignInButton()
    fun enableSignInButton()
    fun hideKeyboard()
    fun openPinCodeEnterSceenForLogin()
    fun getEmail(): String
    fun getPassword(): String
    fun movePasswordCursorToEnd()
}