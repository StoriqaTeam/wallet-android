package com.storiqa.storiqawallet.login_screen

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showPassword()
    fun hidePassword()
    fun moveInputAtTheEnd()
    fun hideEmailError()
    fun hidePasswordError()
    fun showVerificationError()
    fun enableSignInButton()
    fun disableSignInButton()
    fun startMainScreen()
    fun setEmailError(error: String)
    fun setPasswordError(error: String)
}