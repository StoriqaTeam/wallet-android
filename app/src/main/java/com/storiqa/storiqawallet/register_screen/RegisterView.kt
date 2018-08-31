package com.storiqa.storiqawallet.register_screen

import com.arellomobile.mvp.MvpView

interface RegisterView : MvpView {
    fun startLoginScreen()
    fun changePasswordVisibility()
    fun changeRepeatedPasswordVisibility()
    fun enableSignUpButton()
    fun disableSignUpButton()
    fun showPasswordsHaveToMatchError()
    fun hidePasswordsHaveToMatchError()
    fun showRegistrationSuccessDialog()
    fun showRegistrationError()
    fun setEmailError(emailError: String)
    fun setPasswordError(passwordError: String)
    fun clearErrors()


}