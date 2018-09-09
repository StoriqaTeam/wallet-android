package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import com.arellomobile.mvp.MvpView

interface NewPasswordEnterView : MvpView {
    fun goBack()
    fun showPasswordsNotMatchError()
    fun startLoginScreen()
    fun showGeneralError()
    fun showProgress()
    fun hideProgress()
}