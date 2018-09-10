package com.storiqa.storiqawallet.screen_login

import android.os.Message
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
    fun startRegisterScreen()
    fun showSignInError()
    fun openRecoverPasswordScreen()
    fun startQuickLaunchScreen()
    fun disableSignInButton()
    fun enableSignInButton()
    fun hideKeyboard()
    fun openPinCodeEnterSceenForLogin()
}