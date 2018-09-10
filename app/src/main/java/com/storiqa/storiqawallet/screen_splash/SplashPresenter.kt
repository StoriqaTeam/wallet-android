package com.storiqa.storiqawallet.screen_splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class SplashPresenter : MvpPresenter<SplashView>() {
    val model = SplashModelImp()

    fun initScreen() {
        viewState.startShowButtonsAnimation()
    }

    fun onGetStartedButtonClicked() {
        model.setUserWentSplash()
        viewState.startRegisterScreen()
    }

    fun onSignInButtonClicked() {
        model.setUserWentSplash()
        viewState.startLoginScreen()
    }

    fun redirectIfUserSawSplash() {
        if(model.isUserWentFromSplash()) {
            viewState.startLoginScreen()
        }
    }

}