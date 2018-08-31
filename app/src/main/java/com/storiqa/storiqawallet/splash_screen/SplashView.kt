package com.storiqa.storiqawallet.splash_screen

import com.arellomobile.mvp.MvpView

interface SplashView : MvpView {
    fun startShowButtonsAnimation()
    fun startResizeLogoAnimation()
    fun startLoginScreen()
    fun startMoveLogoUpAnimation()
    fun startRegisterScreen()
}