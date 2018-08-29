package com.storiqa.storiqawallet.splash_screen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {
    val model = SplashModelImp()

    fun initScreen() {
        viewState.startShowButtonsAnimation()
        viewState.startResizeLogoAnimation()
        viewState.startMoveLogoUpAnimation()
    }

    fun onGetStartedButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onSignInButtonClicked() {
        viewState.startLoginScreen()
    }


}