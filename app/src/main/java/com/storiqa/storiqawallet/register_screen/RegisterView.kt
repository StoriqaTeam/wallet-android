package com.storiqa.storiqawallet.register_screen

import com.arellomobile.mvp.MvpView

interface RegisterView : MvpView {
    fun startLoginScreen()
    fun changePasswordVisibility()
    fun changeRepeatedPasswordVisibility()


}