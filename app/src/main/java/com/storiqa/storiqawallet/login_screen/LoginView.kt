package com.storiqa.storiqawallet.login_screen

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showPassword()
    fun hidePassword()
    fun moveInputAtTheEnd()
    fun showEmailIsNotVerifiedError()
    fun hideEmailIsNotVerifiedError()
    fun showPasswordError()
    fun hidePasswordError()
    fun verificationError()
    fun enableSignInButton()
    fun disableSignInButton()

}